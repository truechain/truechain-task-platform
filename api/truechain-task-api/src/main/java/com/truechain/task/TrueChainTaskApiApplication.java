package com.truechain.task;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;

@SpringBootApplication
@EnableTransactionManagement
public class TrueChainTaskApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrueChainTaskApiApplication.class, args);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(@Autowired EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
