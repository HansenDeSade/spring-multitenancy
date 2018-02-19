package de.hansendesade.multitenancy.multitenancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    private MultitenancyProperties multitenancyProperties;

    @Bean(name = { "dataSource", "tenant1" })
    @ConfigurationProperties(prefix = "spring.multitenancy.tenant1")
    public DataSource tenant1() {
        DataSourceBuilder factory = DataSourceBuilder
                .create(this.multitenancyProperties.getTenant1().getClassLoader())
                .driverClassName(this.multitenancyProperties.getTenant1().getDriverClassName())
                .username(this.multitenancyProperties.getTenant1().getUsername())
                .password(this.multitenancyProperties.getTenant1().getPassword())
                .url(this.multitenancyProperties.getTenant1().getUrl());
        return factory.build();
    }

    @Bean(name = "tenant2")
    @ConfigurationProperties(prefix = "spring.multitenancy.tenant2")
    public DataSource tenant2() {
        DataSourceBuilder factory = DataSourceBuilder
                .create(this.multitenancyProperties.getTenant2().getClassLoader())
                .driverClassName(this.multitenancyProperties.getTenant2().getDriverClassName())
                .username(this.multitenancyProperties.getTenant2().getUsername())
                .password(this.multitenancyProperties.getTenant2().getPassword())
                .url(this.multitenancyProperties.getTenant2().getUrl());
        return factory.build();
    }

    @Bean(name = "tenant3")
    @ConfigurationProperties(prefix = "spring.multitenancy.tenant3")
    public DataSource tenant3() {
        DataSourceBuilder factory = DataSourceBuilder
                .create(this.multitenancyProperties.getTenant3().getClassLoader())
                .driverClassName(this.multitenancyProperties.getTenant3().getDriverClassName())
                .username(this.multitenancyProperties.getTenant3().getUsername())
                .password(this.multitenancyProperties.getTenant3().getPassword())
                .url(this.multitenancyProperties.getTenant3().getUrl());
        return factory.build();
    }
}
