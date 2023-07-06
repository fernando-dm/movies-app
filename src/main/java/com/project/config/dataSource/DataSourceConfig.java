package com.project.config.dataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

    // Los ds proveen formas para los clientes de conectarse a jdbc
    // hay varios, tomcatCP , HikariCP (connection pool)
    @Bean
    @Primary    //si tengo varios ds, elijo 1 como primary
    @ConfigurationProperties("app.datasource.main") // tomo la conf del yaml de application == property
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();}

    // si hay multiples DS , la forma de trabajar es creando un jdbcTemp para cada uno
    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource){

        return new JdbcTemplate(hikariDataSource);
    }
}
