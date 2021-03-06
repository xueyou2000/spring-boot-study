package com.xueyou.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * 创建 by xueyo on 2019/7/18
 */
@Configuration
@EntityScan(basePackages = "com.xueyou.model")
@EnableJpaRepositories(basePackages = "com.xueyou.repository")
@EnableTransactionManagement
public class ApplicationConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.xueyou");
        factory.setDataSource(dataSource);
        Properties properties = new Properties();
        properties.setProperty("hibernate.format_sql", "true");
        factory.setJpaProperties(properties);
        return  factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return  transactionManager;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        // 使用QueryDSL的功能时，会依赖使用到JPAQueryFactory
        return new JPAQueryFactory(entityManager);
    }

}
