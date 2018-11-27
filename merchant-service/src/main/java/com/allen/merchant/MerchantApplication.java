package com.allen.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * Hello world!
 */
@Import(value = org.mengyun.tcctransaction.spring.config.TccConfig.class)
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class MerchantApplication {
    public static void main(String[] args) {

        SpringApplication.run(MerchantApplication.class, args);
    }
}
