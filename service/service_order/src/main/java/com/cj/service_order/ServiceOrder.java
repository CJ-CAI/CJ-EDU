package com.cj.service_order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.atguigu"})
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.cj.service_order.mapper")
public class ServiceOrder {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrder.class,args);
    }
}
