package cn.afterturn.easypoi.test;

import org.junit.Test;

public class UtilTest {

    @Test
    public void test() {
        String text = " {{   p    in    pList}}";
        text = text.replace("{{", "").replace("}}", "").replaceAll("\\s{1,}", " ").trim();
        System.out.println(text);
        System.out.println(text.length());
        
        
        
    }

}
