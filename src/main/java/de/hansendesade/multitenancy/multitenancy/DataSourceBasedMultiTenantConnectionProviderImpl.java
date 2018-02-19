package de.hansendesade.multitenancy.multitenancy;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * we extend the AbstractDataSourceBasedMultiTenantConnectionProviderImpl provided by Hibernate, we let Spring inject
 * our datasources, load them in a map  and resolve the datasource based on the tenantIdentifier.
 *
 * http://anakiou.blogspot.de/2015/08/multi-tenant-application-with-spring.html
 */
@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final String DEFAULT_TENANT_ID = "tenant1";
    private Map<String, DataSource> map;

    @Autowired
    private DataSource tenant1;

    @Autowired
    private DataSource tenant2;

    @Autowired
    private DataSource tenant3;

    @PostConstruct
    public void load() {
        map = new HashMap<>();
        map.put("tenant1", tenant1);
        map.put("tenant2", tenant2);
        map.put("tenant3", tenant3);
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return map.get(DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return map.get(tenantIdentifier);
    }
}
