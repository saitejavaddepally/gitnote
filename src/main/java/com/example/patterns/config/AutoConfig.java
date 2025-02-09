package com.example.patterns.config;

import com.example.patterns.vo.TbCsmUserArticleVo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfig {

    AutoConfig(){
        System.out.println("I am getting constructed first");
    }

    @PostConstruct
    public void test3(){
        System.out.println("In post construct");
    }

    @Bean
    public TbCsmUserArticleVo test(){
        System.out.println("In test Bean");
        return new TbCsmUserArticleVo();
    }

    @Bean
    public TbCsmUserArticleVo test2(){
        System.out.println("In test Bean2");
        return new TbCsmUserArticleVo();
    }

    @PreDestroy
    public void test4(){
        System.out.println("In destroy bean !!");
    }

}
