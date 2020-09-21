package com.xknower.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 微信公共号服务
 *
 * @author xknower
 * @date 2020-09-21
 */
@SpringBootApplication
public class ApiServerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder(ApiServerApplication.class).run();
    }
}
