package com.mayikt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mayikt.loadbalancer.LoadBalancer;
@RestController
public class OrderService {
	@Autowired
	private DiscoveryClient discoveryClient;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private LoadBalancer loadBalancer;
	//订单项目调用到我们会员服务的接口
	@RequestMapping("/orderToMember")
	public Object orderToMember() {
		/*
		 * 1,根据服务名称从注册中心获取集群列表地址
		 * 2,列表任意(负载均衡)选择一个实现本地rpc(远程)调用rest
		 */
		List<ServiceInstance> instances = discoveryClient.getInstances("meitemayikt-member");
		/*
		 * Rpc远程调用涉及到本地负载均衡算法,从注册中心获取服务集群列表,然后从列表里面选择一个服务
		 * 负载均衡算法有哪些:
		 * 1,一致性hash计算
		 * 2,轮训
		 * 3,权重
		 * 4,随机
		 * 这种实现一般都采用策略设计模式
		 */
		//ServiceInstance serviceInstance = instances.get(0);
		ServiceInstance serviceInstance = loadBalancer.getSigleAddress(instances);//负载均衡
		System.out.println("从Nacos注册中心上面获取的生产者的接口地址:" + serviceInstance.getUri());
		//使用restTemplate实现RPC远程调用
		String result = restTemplate.getForObject(serviceInstance.getUri() + "/getUser", String.class);
		return "消费者订单项目调用生产者会员项目返回结果为:" + result;
	}
	
	//订单项目调用到我们会员服务的接口
	@RequestMapping("/orderToRibbonMember")
	public Object orderToRibbonMember() {//基于Ribbon实现本地的负载均衡
		//使用restTemplate实现RPC远程调用,通过在restTemplate上面添加注解@LoadBalanced启用Ribbon负载均衡
		String result = restTemplate.getForObject("http://meitemayikt-member/getUser", String.class);
		return "Robbion本地负载均衡_消费者订单项目调用生产者会员项目返回结果为:" + result;
	}
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	/*
	 * 直接使用接口LoadBalancerClient来实现负载均衡
	 * @LoadBalanced这个注解在spring-cloud-commons-2.0.3.RELEASE.jar这个jar包里面。
	 * 这个jar里面还有一个接口LoadBalancerClient,这个接口的实现RibbonLoadBalancerClient类在
     * spring-cloud-netflix-ribbon-2.0.3.RELEASE.jar这个jar包里面。
	 */
	@RequestMapping("/loadBalancerClientMember")
	public Object loadBalancerClientMember() {
		ServiceInstance serviceInstance = loadBalancerClient.choose("meitemayikt-member");
		return "直接使用接口LoadBalancerClient来实现负载均衡_返回结果为:" + serviceInstance;
	}
}
