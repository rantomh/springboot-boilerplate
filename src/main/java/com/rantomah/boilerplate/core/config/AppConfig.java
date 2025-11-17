package com.rantomah.boilerplate.core.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableAspectJAutoProxy
@EnableFeignClients
@EnableJpaAuditing
public class AppConfig {}
