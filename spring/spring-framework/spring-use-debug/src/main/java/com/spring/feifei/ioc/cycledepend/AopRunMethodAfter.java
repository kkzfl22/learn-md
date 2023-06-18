/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.spring.feifei.ioc.cycledepend;

/**
 * @author liujun
 * @since 2023/4/7
 */
public class AopRunMethodAfter {

	public void afterMethod() {
		System.out.println("执行B的后置通知");
	}
}
