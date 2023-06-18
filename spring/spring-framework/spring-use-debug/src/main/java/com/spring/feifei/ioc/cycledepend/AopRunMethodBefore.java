/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.spring.feifei.ioc.cycledepend;

/**
 * @author liujun
 * @since 2023/4/7
 */
public class AopRunMethodBefore {

	public void beforeMethod() {
		System.out.println("执行A的前置通知");
	}
}
