package com.megalith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-22
 * @Time: 14:46
 */
@SpringBootApplication(scanBasePackages = {"com.trans","com.megalith"})
public class MoneyApplication {

    public static void main(String[] args) {

        SpringApplication.run(MoneyApplication.class,args);
    }
}
