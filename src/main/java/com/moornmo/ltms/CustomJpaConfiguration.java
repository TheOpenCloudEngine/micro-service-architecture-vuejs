package com.moornmo.ltms;

import com.moornmo.ltms.Product;
import org.eclipse.persistence.config.BatchWriting;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.logging.SessionLog;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marc Nuri on 2015-12-05.
 */
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories()
//@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class CustomJpaConfiguration extends JpaBaseConfiguration  {

//    public CustomJpaConfiguration() {
//        super(dataSource(), null, null, null);
//
//    }

    public CustomJpaConfiguration(DataSource dataSource, JpaProperties jpaProperties, ObjectProvider<JtaTransactionManager> jtaTransactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, jpaProperties, jtaTransactionManager, transactionManagerCustomizers);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean ret = null;
        try {
            ret = builder
                    .dataSource(dataSource())
                    .packages(Product.class.getPackage().getName())
                    .persistenceUnit("YourPersistenceUnitName")
                    .properties(initJpaProperties()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    private final Map<String, ?> initJpaProperties() {
        final Map<String, Object> ret = new HashMap<>();
        // Add any JpaProperty you are interested in and is supported by your Database and JPA implementation
        ret.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
        ret.put(PersistenceUnitProperties.LOGGING_LEVEL, SessionLog.FINE_LABEL);
        return ret;
    }

    @Bean
    public DataSource dataSource() {
        //In classpath from spring-boot-starter-web
        final Properties pool = new Properties();
        pool.put("driverClassName", "org.postgresql.Driver");
        pool.put("url", "jdbc:postgresql://example.com:5432/DatabaseName");
        pool.put("username", "user");
        pool.put("password", "password");
        pool.put("minIdle", 1);
        try {
            return new org.apache.tomcat.jdbc.pool.DataSourceFactory().createDataSource(pool);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        final Map<String, Object> ret = new HashMap<>();
        ret.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
        return ret;
    }
}