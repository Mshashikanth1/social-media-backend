package com.slip.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

//@Configuration
//@EnableWebMvc
//public class CorsConfig implements WebMvcConfigurer {
//    private static final String[] CLASSPATH_RESOURCE_LOCATIONS =
//            {
//                    "classpath:/META-INF/resources/",
//                    "classpath:/resources/",
//                    "classpath:/static/",
//                    "classpath:/public/",
//                    "classpath:/custom/",
//                    "file:/opt/myfiles/",
//                    "/resources/",
//                    "/other-resources/",
//                    "/resources/**",
//                    "/resources/static/**",
//                    "/resources/static/css/**",
//                    "/resources/static/js/**",
//            };
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/resources/**")
//                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
//                .setCachePeriod(3600)
//                .resourceChain(true)
//                .addResolver(new PathResourceResolver());
//    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*") // Add the origin of your React app
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(false)
//                .maxAge(3600);
//    }
//}
