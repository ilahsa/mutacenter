package com.ifeng.ad.mutacenter.initialize;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * mysql初始化
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.mysql")
public class MysqlProvider {
    private Logger logger = LoggerFactory.getLogger(MysqlProvider.class);

    private String driverClassName;
    private String jdbcUrl;
    private String username;
    private String password;
    private boolean readOnly;
    private Long connectionTimeout;
    private Long idleTimeout;
    private Long maxLifetime;
    private int maximumPoolSize;

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setConnectionTimeout(Long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setIdleTimeout(Long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public void setMaxLifetime(Long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    @Primary
    @Bean(name = "hikariDataSource")
    @Qualifier("hikariDataSource")
    public HikariDataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setReadOnly(readOnly);
        hikariConfig.setConnectionTimeout(connectionTimeout);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setMaxLifetime(maxLifetime);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        logger.info("MySQL连接池创建完成！");
        return hikariDataSource;
    }

    @Bean(name = "queryRunner")
    public QueryRunner queryRunner(@Qualifier("hikariDataSource") HikariDataSource hikariDataSource) {
        return new QueryRunner(hikariDataSource);
    }
}
