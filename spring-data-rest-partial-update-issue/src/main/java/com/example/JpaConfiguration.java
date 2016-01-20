package com.example;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import com.google.common.collect.ImmutableMap;

@Configuration
@EntityScan
public class JpaConfiguration extends JpaBaseConfiguration {

    @Value("${spring.jpa.show-sql}")
    private boolean showSql;

    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        final ImmutableMap<String, Object> immutableMap = ImmutableMap.<String, Object>builder() //
                .build();
        return newHashMap(immutableMap);
    }

    @Autowired
    private LoadTimeWeaver loadTimeWeaver;

    @Override
    protected EntityManagerFactoryBuilder.EntityManagerFactoryBeanCallback getVendorCallback() {
        return factory -> {
            factory.setLoadTimeWeaver(loadTimeWeaver);
        };
    }

    @Override
    protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
        vendorProperties.put(PersistenceUnitProperties.LOGGING_PARAMETERS, String.valueOf(showSql));
    }
}
