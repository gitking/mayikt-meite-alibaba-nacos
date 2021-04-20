package com.mayikt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//这里不用像Eureka那样加一个类似@EnableEureka的注解
public class AppSpringBoot {
	public static void main(String[] args) {
		/**
		 * 本项目meite-member演示Nacos的服务注册公功能
		 * 启动本项目之前,要先启动nacos服务。
		 * 我们这个项目meite-member是服务提供者,相当于生产者项目,提供会员服务
		 * 启动启动成功之后,会自动把自己注册到nacos上面,可以访问
		 * http://10.10.12.28:8848/nacos/index.html#/serviceManagement?dataId=&group=&appName=&namespace=&pageSize=&pageNo=&serverId=
		 * 查看
		 */
		SpringApplication.run(AppSpringBoot.class, args);
	}
}
