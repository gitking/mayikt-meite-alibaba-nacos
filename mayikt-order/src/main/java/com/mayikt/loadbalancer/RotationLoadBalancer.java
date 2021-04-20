package com.mayikt.loadbalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

/*
 * 负载均衡轮训算法实现:
 * 假设我们有俩台服务器需要轮询,那么算法就是这样算的:访问次数%集群size
 * 第一次访问
 * 1%2=1
 * 2%2=0
 * 3%2=1
 * 4%2=0
 * 假设只有1台服务器
 * 1%1=0(通过下标访问List刚好是0)
 */
@Component
public class RotationLoadBalancer implements LoadBalancer {
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	@Override
	public ServiceInstance getSigleAddress(List<ServiceInstance> serviceInstances) {
		int index = atomicInteger.incrementAndGet() % serviceInstances.size();
		return serviceInstances.get(index);
	}
}
