package com.deu.marketplace;

import com.deu.marketplace.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceApplication.class, args);
    }

//    @Bean
//    public AuditorAware<String> auditorProvider() {
//        return () -> Optional.of(세션정보 or 스프링 시큐리티 로그인 ID)
//    }
}