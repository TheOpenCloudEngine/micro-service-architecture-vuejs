package com.moornmo.ltms;

import hello.PersonRepository;
import org.metaworks.rest.MetaworksRestService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = {MetaworksRestService.class, ProductRepository.class})
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
}