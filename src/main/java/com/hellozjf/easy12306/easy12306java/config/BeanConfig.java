package com.hellozjf.easy12306.easy12306java.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jingfeng Zhou
 */
@Configuration
@Slf4j
public class BeanConfig {

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
        };
    }
}
