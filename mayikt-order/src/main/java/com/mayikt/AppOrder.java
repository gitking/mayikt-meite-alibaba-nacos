package com.mayikt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AppOrder {
	public static void main(String[] args) {
		/**
		 * 本项目mayikt-order演示Nacos的服务发现功能
		 * 本项目是消费者项目,从Nacos注册中心上面获取的生产者的服务地址,然后通过RPC进行远程调用
		 * 启动成功后在浏览器上面访问:http://127.0.0.1:8090/orderToMember
		 */
		SpringApplication.run(AppOrder.class, args);
	}
	@Bean
	@LoadBalanced//让RestTemplate启用Ribbon本地负载均衡
	public RestTemplate restTemplate() {
		/*
		 * @LoadBalanced这个注解在spring-cloud-commons-2.0.3.RELEASE.jar这个jar包里面。
		 * 这个jar里面还有一个接口LoadBalancerClient,这个接口的实现RibbonLoadBalancerClient类在
		 * spring-cloud-netflix-ribbon-2.0.3.RELEASE.jar这个jar包里面。
		 */
		return new RestTemplate();
	}
}
