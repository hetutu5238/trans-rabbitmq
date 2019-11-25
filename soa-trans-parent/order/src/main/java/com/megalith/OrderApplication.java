package com.megalith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-22
 * @Time: 14:45
 */
@SpringBootApplication(scanBasePackages = {"com.trans","com.megalith"})
public class OrderApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrderApplication.class,args);
    }
}
