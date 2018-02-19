package de.hansendesade.multitenancy.multitenancy;

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
    private DataSourceProperties tenant1;

    @NestedConfigurationProperty
    private DataSourceProperties tenant2;

    @NestedConfigurationProperty
    private DataSourceProperties tenant3;

    public DataSourceProperties getTenant1() {
        return tenant1;
    }

    public void setTenant1(DataSourceProperties tenant1) {
        this.tenant1 = tenant1;
    }

    public DataSourceProperties getTenant2() {
        return tenant2;
    }

    public void setTenant2(DataSourceProperties tenant2) {
        this.tenant2 = tenant2;
    }

    public DataSourceProperties getTenant3() {
        return tenant3;
    }

    public void setTenant3(DataSourceProperties tenant3) {
        this.tenant3 = tenant3;
    }
}
