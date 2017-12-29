package de.evelopment.multitenancy.multitenancy;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * It is probably a good idea to externalise the configuration for our datasources so we will use the @Configuration
 * annotation and Spring will validate and bind the datasources from application.properties.
 *
 * http://anakiou.blogspot.de/2015/08/multi-tenant-application-with-spring.html
 *
 * A next step could be to externalise the configuration to a meta database to allow dynamic creation of new tenants.
 *
 * https://howtodoinjava.com/spring/spring-orm/spring-3-2-5-abstractroutingdatasource-example/
 */
@ConfigurationProperties("spring.multitenancy")
public class MultitenancyProperties {

    @NestedConfigurationProperty
    private DataSourceProperties localhost;

    @NestedConfigurationProperty
    private DataSourceProperties schneider;

    @NestedConfigurationProperty
    private DataSourceProperties popken;

    public DataSourceProperties getLocalhost() {
        return localhost;
    }

    public void setLocalhost(DataSourceProperties localhost) {
        this.localhost = localhost;
    }

    public DataSourceProperties getSchneider() {
        return schneider;
    }

    public void setSchneider(DataSourceProperties schneider) {
        this.schneider = schneider;
    }

    public DataSourceProperties getPopken() {
        return popken;
    }

    public void setPopken(DataSourceProperties popken) {
        this.popken = popken;
    }
}
