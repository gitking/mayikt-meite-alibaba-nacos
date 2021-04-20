package com.mayikt.loadbalancer;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

public interface LoadBalancer {
	/*
	 * 从注册中心集群列表中根据算法获取单个地址
	 */
	ServiceInstance getSigleAddress(List<ServiceInstance> serviceInstances);
}
