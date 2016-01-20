package com.example;

import static org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving.DISABLED;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringConfigured
@EnableLoadTimeWeaving(aspectjWeaving = DISABLED)
@EnableSpringDataWebSupport
public class SpringDataRestPartialUpdateIssueApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRestPartialUpdateIssueApplication.class, args);
    }
}
