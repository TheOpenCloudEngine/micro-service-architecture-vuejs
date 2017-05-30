package com.moornmo.ltms;

import org.metaworks.multitenancy.ClassManager;
import org.metaworks.multitenancy.CouchbaseMetadataService;
import org.metaworks.multitenancy.MetadataService;
import org.metaworks.rest.MetaworksRestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.uengine.modeling.resource.CachedResourceManager;
import org.uengine.modeling.resource.ResourceManager;
import org.uengine.modeling.resource.Storage;
import org.uengine.persistence.couchbase.CouchbaseStorage;

import java.util.List;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = {MetaworksRestService.class, ProductRepository.class, ClassManager.class, MetadataService.class})
public class WebConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new GsonHttpMessageConverter());
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081")
                .allowedMethods("POST", "GET", "PUT", "DELETE");

//
//        registry.addMapping("/**").allowedOrigins("*");
//        registry.addMapping("/people").allowedOrigins("*");
//        registry.addMapping("/**").allowedOrigins("http://localhost:8081");
//        registry.addMapping("/people").allowedOrigins("http://localhost:8081");
    }

    @Bean
    public ResourceManager resourceManager(){
        ResourceManager resourceManager = new CachedResourceManager();
        resourceManager.setStorage(storage());
        return resourceManager;
    }

    @Bean
    /**
     *
     * <property name="basePath" value="/"/>
     <property name="bucketName" value="default"/>
     <property name="serverIp" value="localhost"/>

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
}