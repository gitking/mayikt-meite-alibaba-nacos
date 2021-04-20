package com.mayikt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberService {
	@Value("${server.port}")
	private String serverPort;
	/*
	 * 会员服务提供的接口被订单服务调用
	 */
	@GetMapping("/getUser")
	public String getUser(Integer userId) {
		return "每特教育|蚂蚁课堂,端口号为:" + serverPort;
	}
}
