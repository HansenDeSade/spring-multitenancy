package de.evelopment.multitenancy.multitenancy;

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

    @Bean(name = { "dataSource", "localhost" })
    @ConfigurationProperties(prefix = "spring.multitenancy.localhost")
    public DataSource localhost() {
        DataSourceBuilder factory = DataSourceBuilder
                .create(this.multitenancyProperties.getLocalhost().getClassLoader())
                .driverClassName(this.multitenancyProperties.getLocalhost().getDriverClassName())
                .username(this.multitenancyProperties.getLocalhost().getUsername())
                .password(this.multitenancyProperties.getLocalhost().getPassword())
                .url(this.multitenancyProperties.getLocalhost().getUrl());
        return factory.build();
    }

    @Bean(name = "schneider")
    @ConfigurationProperties(prefix = "spring.multitenancy.schneider")
    public DataSource schneider() {
        DataSourceBuilder factory = DataSourceBuilder
                .create(this.multitenancyProperties.getSchneider().getClassLoader())
                .driverClassName(this.multitenancyProperties.getSchneider().getDriverClassName())
                .username(this.multitenancyProperties.getSchneider().getUsername())
                .password(this.multitenancyProperties.getSchneider().getPassword())
                .url(this.multitenancyProperties.getSchneider().getUrl());
        return factory.build();
    }

    @Bean(name = "popken")
    @ConfigurationProperties(prefix = "spring.multitenancy.popken")
    public DataSource popken() {
        DataSourceBuilder factory = DataSourceBuilder
                .create(this.multitenancyProperties.getPopken().getClassLoader())
                .driverClassName(this.multitenancyProperties.getPopken().getDriverClassName())
                .username(this.multitenancyProperties.getPopken().getUsername())
                .password(this.multitenancyProperties.getPopken().getPassword())
                .url(this.multitenancyProperties.getPopken().getUrl());
        return factory.build();
    }
}
