package com.pingan.jsr269ast.processor;

import com.pingan.jsr269ast.annotation.EnumInnerConstant;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * generate inner-class containing outer-class's all public-static-final parameters
 *
 * @author JustryDeng
 * @date 2020/5/13 20:53:30
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.pingan.jsr269ast.annotation.EnumInnerConstant")
public class EnumInnerConstantProcessor extends AbstractProcessor {

    /** 消息记录器 */
    private Messager messager;

    /** 可将Element转换为JCTree的工具。(注: 简单的讲，处理AST, 就是处理一个又一个CTree) */
    private JavacTrees trees;

    /** JCTree制作器 */
    private TreeMaker treeMaker;

    /** 名字处理器*/
    private Names names;

    /** 内部类类名校验 */
    private static final String INNER_CLASS_NAME_REGEX = "[A-Z][A-Za-z0-9]+";
    private static final Pattern INNER_CLASS_NAME_PATTERN = Pattern.compile(INNER_CLASS_NAME_REGEX);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "roundEnv -> " + roundEnv);
        // 获取被@EnumInnerConstant注解标记的所有元素(可能是类、变量、方法等等)
        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(EnumInnerConstant.class);
        elementSet.forEach(element -> {
            /*
             * 存储参数名与参数值的map、存储参数名与参数类型的map、一个辅助计数的map
             * <p>
             * 注: 照理来说，这里是单线程的。但考虑到本人对AST的处理机制也不是很熟，为
             *     保证万无一失，这里直接用线程安全的类吧。
             */
            Map<String, Object> paramsNameValueMap = new ConcurrentHashMap<>(8);
            Map<String, JCTree.JCExpression> paramsNameTypeMap = new ConcurrentHashMap<>(8);
            Map<String, AtomicInteger> paramIndexHelper = new ConcurrentHashMap<>(4);

            // 获取到注解信息
            EnumInnerConstant annotation = element.getAnnotation(EnumInnerConstant.class);
            String originInnerClassName = annotation.innerClassName();
            // 内部类类名校验
            String innerClassName = checkInnerClassName(originInnerClassName);
            // 将Element转换为JCTree
            JCTree jcTree = trees.getTree(element);
            String className = (((JCTree.JCClassDecl)jcTree).sym).type.toString();
            String enumFlag = "enum";
            if (!enumFlag.equalsIgnoreCase(jcTree.getKind().name())) {
                // 只要用的是Diagnostic.Kind.ERROR输出的消息，那么编译时就会自动编译失败的
                messager.printMessage(Diagnostic.Kind.ERROR, "@EnumInnerConstant only support enum-class, ["
                        + className + "] is not supported");
            }
            /*
             * 通过JCTree.accept(JCTree.Visitor)访问JCTree对象的内部信息。
             *
             * JCTree.Visitor有很多方法，我们可以通过重写对应的方法,(从该方法的形参中)来获取到我们想要的信息:
             * 如: 重写visitClassDef方法， 获取到类的信息;
             *     重写visitMethodDef方法， 获取到方法的信息;
             *     重写visitVarDef方法， 获取到变量的信息;
             *     重写visitLabelled方法， 获取到常量的信息;
             *     重写visitBlock方法， 获取到方法体的信息;
             *     重写visitImport方法， 获取到导包信息;
             *     重写visitForeachLoop方法， 获取到for循环的信息;
             *     ......
             */
            jcTree.accept(new TreeTranslator() {

                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    // 不要放在 jcClassDecl.defs = jcClassDecl.defs.append(a);之后，否者会递归
                    super.visitClassDef(jcClassDecl);
                    // 生成内部类， 并将内部类写进去
                    JCTree.JCClassDecl innerClass = generateInnerClass(innerClassName, paramsNameValueMap, paramsNameTypeMap);
                    jcClassDecl.defs = jcClassDecl.defs.append(innerClass);
                }

                @Override
                public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
                    boolean isEnumConstant = className.equals(jcVariableDecl.vartype.type.toString());
                    if (!isEnumConstant) {
                        super.visitVarDef(jcVariableDecl);
                        return;
                    }
                    Name name = jcVariableDecl.getName();
                    String paramName = name.toString();
                    /*
                     * 枚举项本身也属于变量, 每个枚举项里面，可能还有变量。 这里继
                     * 续JCTree.accept(JCTree.Visitor)进入，访问这个枚举项的内部信息。
                     */
                    jcVariableDecl.accept(new TreeTranslator() {
                        @Override
                        public void visitLiteral(JCTree.JCLiteral jcLiteral) {
                            Object paramValue = jcLiteral.getValue();
                            if (paramValue == null) {
                                return;
                            }
                            TypeTag typetag = jcLiteral.typetag;
                            JCTree.JCExpression paramType;
                            if (isPrimitive(typetag)) {
                                // 如果是基本类型，那么可以直接生成
                                paramType = treeMaker.TypeIdent(typetag);
                            } else if (paramValue instanceof String) {
                                // 如果不是基本类型，那么需要拼接生成
                                paramType = generateJcExpression("java.lang.String");
                            } else {
                                return;
                            }
                            AtomicInteger atomicInteger = paramIndexHelper.get(paramName);
                            if (atomicInteger == null) {
                                atomicInteger = new AtomicInteger(0);
                                paramIndexHelper.put(paramName, atomicInteger);
                            }
                            int paramIndex = atomicInteger.getAndIncrement();
                            String key = paramName + "_" + paramIndex;
                            paramsNameTypeMap.put(key, paramType);
                            paramsNameValueMap.put(key, paramValue);
                            super.visitLiteral(jcLiteral);
                        }
                    });
                    super.visitVarDef(jcVariableDecl);
                }
            });
        });
        return false;
    }

    /**
     * 内部类类名 合法性校验
     *
     * @param innerClassName
     *            内部类类名
     * @return  校验后的内部类类名
     * @date 2020/5/17 13:45:27
     */
    private String checkInnerClassName (String innerClassName) {
        if (innerClassName == null || innerClassName.trim().length() == 0) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@EnumInnerConstant. inner-class-name cannot be empty");
            return innerClassName;
        }
        innerClassName = innerClassName.trim();
        if (!INNER_CLASS_NAME_PATTERN.matcher(innerClassName).matches()) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@EnumInnerConstant. inner-class-name must match regex "
                    + INNER_CLASS_NAME_REGEX);
        }
        return innerClassName;
    }

    /**
     * 判断typeTag是否属于基本类型
     *
     * @param typeTag
     *            typeTag
     * @return 是否属于基本类型
     * @date 2020/5/17 13:10:54
     */
    private boolean isPrimitive(TypeTag typeTag) {
        if (typeTag == null) {
            return false;
        }
        TypeKind typeKind;
        try {
            typeKind = typeTag.getPrimitiveTypeKind();
        } catch (Throwable e) {
            return false;
        }
        if (typeKind == null) {
            return false;
        }
        return typeKind.isPrimitive();
    }


    /**
     * 生成内部类
     *
     * @return 生成出来的内部类
     * @date 2020/5/16 15:43:56
     */
    private JCTree.JCClassDecl generateInnerClass(String innerClassName, Map<String, Object> paramsInfoMap,Map<String, JCTree.JCExpression> paramsNameTypeMap) {
        JCTree.JCClassDecl jcClassDecl1 = treeMaker.ClassDef(
                treeMaker.Modifiers(Flags.PUBLIC + Flags.STATIC),
                names.fromString(innerClassName),
                List.nil(),
                null,
                List.nil(),
                List.nil());
        List<JCTree.JCVariableDecl> collection = generateAllParameters(paramsInfoMap, paramsNameTypeMap);
        collection.forEach(x -> jcClassDecl1.defs = jcClassDecl1.defs.append(x));
        return jcClassDecl1;
    }

    /**
     * 生成参数
     *
     * @param paramNameValueMap
     *           参数名-参数值map
     * @param paramsNameTypeMap
     *           参数名-参数类型map
     *
     * @return  参数JCTree集合
     * @date 2020/5/16 15:44:47
     */
    private List<JCTree.JCVariableDecl> generateAllParameters(Map<String, Object> paramNameValueMap,
                                                              Map<String, JCTree.JCExpression> paramsNameTypeMap) {
        List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();
        JCTree.JCVariableDecl statement;
        if (paramNameValueMap != null && paramNameValueMap.size() != 0) {
            for (Map.Entry<String, Object> entry : paramNameValueMap.entrySet()) {
                // 定义变量
                statement = treeMaker.VarDef(
                        // 访问修饰符
                        treeMaker.Modifiers(Flags.PUBLIC + Flags.STATIC + Flags.FINAL),
                        // 参数名
                        names.fromString(entry.getKey()),
                        // 参数类型
                        paramsNameTypeMap.get(entry.getKey()),
                        // 参数值
                        treeMaker.Literal(entry.getValue()));

                jcVariableDeclList = jcVariableDeclList.append(statement);
            }
        }
        return jcVariableDeclList;
    }

    /**
     * 根据全类名获取JCTree.JCExpression
     *
     * 如: 类变量 public static final String ABC = "abc";中, String就需要
     *     调用此方法generateJCExpression("java.lang.String")进行获取。
     *      追注: 其余的复杂类型，也可以通过这种方式进行获取。
     *      追注: 对于基本数据类型，可以直接通过类TreeMaker.TypeIdent获得，
     *           如: treeMaker.TypeIdent(TypeTag.INT)可获得int的JCTree.JCExpression
     *
     * @param fullNameOfTheClass
     *            全类名
     * @return  全类名对应的JCTree.JCExpression
     * @date 2020/5/16 15:47:32
     */
    private JCTree.JCExpression generateJcExpression(String fullNameOfTheClass) {
        String[] fullNameOfTheClassArray = fullNameOfTheClass.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(names.fromString(fullNameOfTheClassArray[0]));
        for (int i = 1; i < fullNameOfTheClassArray.length; i++) {
            expr = treeMaker.Select(expr, names.fromString(fullNameOfTheClassArray[i]));
        }
        return expr;
    }

}