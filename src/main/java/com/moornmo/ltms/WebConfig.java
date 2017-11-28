package com.moornmo.ltms;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.metaworks.multitenancy.ClassManager;
import org.metaworks.multitenancy.CouchbaseMetadataService;
import org.metaworks.multitenancy.DefaultMetadataService;
import org.metaworks.multitenancy.MetadataService;
import org.metaworks.multitenancy.tenantawarefilter.TenantAwareFilter;
import org.metaworks.springboot.configuration.CorsFilter;
import org.metaworks.springboot.configuration.Metaworks4WebConfig;
import org.metaworks.multitenancy.persistence.MultitenantRepositoryImpl;
import org.metaworks.rest.MetaworksRestService;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.uengine.modeling.resource.CachedResourceManager;
import org.uengine.modeling.resource.LocalFileStorage;
import org.uengine.modeling.resource.ResourceManager;
import org.uengine.modeling.resource.Storage;
import org.uengine.persistence.couchbase.CouchbaseStorage;

import javax.sql.DataSource;
import java.util.Properties;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = {TenantAwareFilter.class, MetaworksRestService.class, WebConfig.class, ClassManager.class, MetadataService.class})
@EnableJpaRepositories(repositoryBaseClass = MultitenantRepositoryImpl.class)
public class WebConfig extends Metaworks4WebConfig {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8081", "http://localhost:8082", "*")
//                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("access_token", "Content-Type");
//
//    }

    /**
     * Uncomment if this needs a CORS setting by itself
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }


    @Bean
    public TenantAwareFilter tenantAwareFilter(){
        return new TenantAwareFilter();
    }

    @Bean
    public ResourceManager resourceManager() {
        ResourceManager resourceManager = new CachedResourceManager();
        resourceManager.setStorage(storage());
        return resourceManager;
    }

    @Bean
    /**
     *
     * <bean class="CouchbaseStorage">
     *    <property name="basePath" value="/"/>
     <property name="bucketName" value="default"/>
     <property name="serverIp" value="localhost"/>
     </bean>
     */
    public Storage storage() {
//        CouchbaseStorage storage = new CouchbaseStorage();
//        storage.setBucketName("default");
//        storage.setServerIp("localhost");

        LocalFileStorage storage = new LocalFileStorage();
        storage.setBasePath("/oce/repository");

        return storage;
    }

    @Bean
    public MetadataService metadataService() {
//        CouchbaseMetadataService metadataService = new CouchbaseMetadataService();
//        metadataService.setCouchbaseServerIp("localhost");
//        metadataService.setBucketName("default");

        DefaultMetadataService metadataService = new DefaultMetadataService();
        metadataService.setResourceManager(resourceManager());

        return metadataService;
    }

//    @Bean
//    public DataSource dataSource() {
//        //In classpath from spring-boot-starter-web
//        final Properties pool = new Properties();
//        pool.put("driverClassName", "com.mysql.jdbc.Driver");
//        pool.put("url", "jdbc:mysql://localhost:3306/uengine?useUnicode=true&characterEncoding=UTF8&useOldAliasMetadataBehavior=true");
//        pool.put("username", "root");
//        pool.put("password", "");
//        pool.put("minIdle", 1);
//        try {
//            return new org.apache.tomcat.jdbc.pool.DataSourceFactory().createDataSource(pool);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Bean
    @Primary
    public JpaProperties jpaProperties() {

        JpaProperties propertiesMap = new JpaProperties();
        propertiesMap.getProperties().put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);

        return propertiesMap;
    }


}