package com.example.mdemo;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class MdemoUseApplication /*implements CommandLineRunner */{
	@Autowired
	LoadBalancerClient loadBalancerClient;
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/home2")
	public String home() {
		//获取服务
		ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client-1");
//		System.out.println("uri:"+serviceInstance.getUri());

		//调用接口http://localhost:8081/home
		String resp = restTemplate.getForObject(serviceInstance.getUri()+"/home", String.class);
//		System.out.println("resp:"+resp);
		return resp+2;
	}


	public static void main(String[] args) {
		SpringApplication.run(MdemoUseApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

/*	@Override
	public void run(String... args) throws Exception {
		ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client-1");
		System.out.println("uri:"+serviceInstance.getUri());
		String resp = restTemplate.getForObject(serviceInstance.getUri(), String.class);
		System.out.println("resp:"+resp);
	}*/
}
