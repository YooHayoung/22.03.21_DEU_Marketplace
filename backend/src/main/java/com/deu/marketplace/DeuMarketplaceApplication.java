package com.deu.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class DeuMarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeuMarketplaceApplication.class, args);
    }

//    @Bean
//    public AuditorAware<String> auditorProvider() {
//        return () -> Optional.of(세션정보 or 스프링 시큐리티 로그인 ID)
//    }
}
