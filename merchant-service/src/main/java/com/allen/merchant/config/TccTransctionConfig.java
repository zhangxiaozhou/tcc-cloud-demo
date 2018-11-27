package com.allen.merchant.config;

import com.zaxxer.hikari.HikariDataSource;
import org.mengyun.tcctransaction.TransactionRepository;
import org.mengyun.tcctransaction.serializer.KryoPoolSerializer;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TccTransctionConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public KryoPoolSerializer objectSerializer(){

        return new KryoPoolSerializer();
    }

    @Bean(name = "tccDataSourceProperties")
    @ConfigurationProperties(prefix="tcc.datasource")
    public DataSourceProperties tccDataSourceProperties(){

        return new DataSourceProperties();
    }

    @Bean(name = "tccDataSource")
    public DataSource tccDataSource(@Qualifier(value = "tccDataSourceProperties") DataSourceProperties properties){

        return DataSourceBuilder.create(properties.getClassLoader()).type(HikariDataSource.class)
                .driverClassName(properties.determineDriverClassName())
                .url(properties.determineUrl()).username(properties.determineUsername()).password(properties.determinePassword()).build();

    }

    @Bean
    public TransactionRepository transactionRepository(@Qualifier(value = "tccDataSource") DataSource tccDataSource){

        SpringJdbcTransactionRepository springJdbcTransactionRepository = new SpringJdbcTransactionRepository();

        springJdbcTransactionRepository.setDataSource(tccDataSource);
        springJdbcTransactionRepository.setDomain("MERCHANT");
        springJdbcTransactionRepository.setTbSuffix("_MERCHANT");
        springJdbcTransactionRepository.setSerializer(objectSerializer());


        return springJdbcTransactionRepository;
    }
}
