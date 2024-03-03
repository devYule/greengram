package com.green.greengram4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing
public class Greengram4Application {

    public static void main(String[] args) {
        SpringApplication.run(Greengram4Application.class, args);
    }


    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer handle() {
        return p -> p.setOneIndexedParameters(true); // Pageable 의 첫 페이지를 1 부터 시작하도록 변경
    }

}
