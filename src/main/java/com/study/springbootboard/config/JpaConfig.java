package com.study.springbootboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // Auditing으로 이름 넣기 위해 기본값 설정
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("nana");   // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때, 수정하기
    }
}
