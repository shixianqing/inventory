package com.inventory.inventory.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

/**
 * @Author:shixianqing
 * @Date:2019/1/2114:02
 * @Description: 解决跨域问题
 **/
@Configuration
public class CrossConfig extends DelegatingWebMvcConfiguration {
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*");
    }
}
