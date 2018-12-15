package com.aspire.zkclient.subscribe;

/**
 * command节点数据 模型
 *
 * @author JustryDeng
 * @date 2018/11/29 9:55
 */
public class CommandPOJO {

    /** 指令信息 */
    private String cmd;

    /** 此指令涉及到的configPOJO */
    private ConfigPOJO configPOJO;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public ConfigPOJO getConfigPOJO() {
        return configPOJO;
    }

    public void setConfigPOJO(ConfigPOJO configPOJO) {
        this.configPOJO = configPOJO;
    }

    @Override
    public String toString() {
        return "CommandPOJO{cmd='" + cmd + "', configPOJO=" + configPOJO + '}';
    }

    /**
     * 造点符合逻辑的command节点数据要求的数据，以便 拿此数据直接使用ZkClient命令行客户端测试
     *
     * @author JustryDeng
     * @date 2018/11/29 11:20
     */
    public static void main(String[] args) {
        CommandPOJO c1= new  CommandPOJO();
        ConfigPOJO f1 = new ConfigPOJO();
        f1.setNameConfig("测试list");
        c1.setCmd("list");
        c1.setConfigPOJO(f1);

        CommandPOJO c2= new  CommandPOJO();
        ConfigPOJO f2 = new ConfigPOJO();
        f2.setNameConfig("modify");
        c2.setCmd("modify");
        c2.setConfigPOJO(f2);

        CommandPOJO c3= new  CommandPOJO();
        ConfigPOJO f3 = new ConfigPOJO();
        f3.setNameConfig("测试create");
        c3.setCmd("create");
        c3.setConfigPOJO(f3);

        System.out.println(com.alibaba.fastjson.JSON.toJSONString(c1));
        // 输出: {"cmd":"list","configPOJO":{"ageConfig":0,"nameConfig":"测试list"}}

        System.out.println(com.alibaba.fastjson.JSON.toJSONString(c2));
        // 输出: {"cmd":"modify","configPOJO":{"ageConfig":0,"nameConfig":"modify"}}

        System.out.println(com.alibaba.fastjson.JSON.toJSONString(c3));
        // 输出: {"cmd":"create","configPOJO":{"ageConfig":0,"nameConfig":"测试create"}}
    }
}