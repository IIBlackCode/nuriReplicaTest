package com.kbhc.www.DataSource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.kbhc.blackcode.Mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfig {
	
	@Primary
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(
            @Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        // MyBatis 설정 파일 위치 지정
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));

        // Mapper XML 파일 위치 지정
        sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));

        return sessionFactoryBean.getObject();
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(
            @Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        // MyBatis 설정 파일 위치 지정
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));

        // Mapper XML 파일 위치 지정
        sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));

        return sessionFactoryBean.getObject();
    }
}