package com.colorit.backend.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.colorit.backend")//.services.*")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {
}
