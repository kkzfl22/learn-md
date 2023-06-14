package com.spring.feifei.aop.debug;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 测试用户操作
 *
 * @author liujun
 * @version 0.0.1
 */
@EnableTransactionManagement
public class UserOperatorPropertiesTest {

	@Test
	public void userDebug() {
		ApplicationContext context =
				new ClassPathXmlApplicationContext(
						"classpath:/aop/debug/applicationContext-properties.xml");
		UserOperator data = (UserOperator) context.getBean("user");
		System.out.println("容器中的对象:" + data);
		data.login("sysadmin", "sysadmin");
		System.out.println(data.getValue());
	}
}
