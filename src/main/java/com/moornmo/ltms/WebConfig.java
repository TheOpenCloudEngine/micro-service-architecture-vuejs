package com.moornmo.ltms;

import com.moornmo.framework.TenantAwareFilter;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.metaworks.multitenancy.ClassManager;
import org.metaworks.multitenancy.CouchbaseMetadataService;
import org.metaworks.multitenancy.MetadataService;
import org.metaworks.rest.MetaworksRestService;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.uengine.modeling.resource.CachedResourceManager;
import org.uengine.modeling.resource.ResourceManager;
import org.uengine.modeling.resource.Storage;
import org.uengine.persistence.couchbase.CouchbaseStorage;

import javax.sql.DataSource;
import java.util.Properties;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = {TenantAwareFilter.class, MetaworksRestService.class, ProductRepository.class, ClassManager.class, MetadataService.class})
@EnableJpaRepositories(repositoryBaseClass = MultitenantRepositoryImpl.class)
public class WebConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new GsonHttpMessageConverter());
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("access_token", "Content-Type");

//
//        registry.addMapping("/**").allowedOrigins("*");
//        registry.addMapping("/people").allowedOrigins("*");
//        registry.addMapping("/**").allowedOrigins("http://localhost:8081");
//        registry.addMapping("/people").allowedOrigins("http://localhost:8081");
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
        CouchbaseStorage storage = new CouchbaseStorage();
        storage.setBucketName("default");
        storage.setServerIp("localhost");

        return storage;
    }

    @Bean
    public MetadataService metadataService() {
        CouchbaseMetadataService metadataService = new CouchbaseMetadataService();
        metadataService.setCouchbaseServerIp("localhost");
        metadataService.setBucketName("default");

        return metadataService;
    }

    @Bean
    public DataSource dataSource() {
        //In classpath from spring-boot-starter-web
        final Properties pool = new Properties();
        pool.put("driverClassName", "com.mysql.jdbc.Driver");
        pool.put("url", "jdbc:mysql://localhost:3306/uengine?useUnicode=true&characterEncoding=UTF8&useOldAliasMetadataBehavior=true");
        pool.put("username", "root");
        pool.put("password", "");
        pool.put("minIdle", 1);
        try {
            return new org.apache.tomcat.jdbc.pool.DataSourceFactory().createDataSource(pool);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Primary
    public JpaProperties jpaProperties() {

        JpaProperties propertiesMap = new JpaProperties();
        propertiesMap.getProperties().put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);

        return propertiesMap;
    }


//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder) {
//        LocalContainerEntityManagerFactoryBean ret = null;
//        try {
//            ret = builder
//                    .dataSource(dataSource())
//                    .packages(Product.class.getPackage().getName())
//                    .persistenceUnit("YourPersistenceUnitName")
//                    .properties(initJpaProperties()).build();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return ret;
//    }
}