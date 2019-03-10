package cn.mldn.myflume;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFlumeDemo {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestFlumeDemo.class);
	public static void main(String[] args) {
		
		for (int x = 0 ; x < 10 ; x ++) {
			LOGGER.info("mldn.cn");
		} 
	}
}
