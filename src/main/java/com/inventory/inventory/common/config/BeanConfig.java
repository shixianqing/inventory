package com.inventory.inventory.common.config;

import org.jasypt.util.binary.BasicBinaryEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Value("${jasypt.encryptor.password}")
    private String password;

    /**
     * 实例化
     * @return
     */
    @Bean
    public BasicTextEncryptor basicTextEncryptor(){
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(password);
        return basicTextEncryptor;
    }

    @Bean
    public BasicBinaryEncryptor basicBinaryEncryptor(){
        BasicBinaryEncryptor basicBinaryEncryptor = new BasicBinaryEncryptor();
        basicBinaryEncryptor.setPassword(password);
        return basicBinaryEncryptor;
    }
}
