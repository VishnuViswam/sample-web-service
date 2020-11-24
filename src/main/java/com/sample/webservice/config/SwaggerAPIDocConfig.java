package com.sample.webservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Configuration class of Swagger API doc.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Configuration
@EnableSwagger2
public class SwaggerAPIDocConfig {


    public static final Contact DEFAULT_CONTACT = new Contact("Demo", "http://www.demo.ae/",
            "info@demo.ae");

    public static final ApiInfo DEFAUL_API_INFO = new ApiInfo("Sample Application",
            "Sample Application description.",
            "1.0.0",
            "http://www.sampleapplication.ae/",
            DEFAULT_CONTACT, "Open licence",
            "http://www.sampleapplication.ae/#license",
            new ArrayList<VendorExtension>());

    private static final Set<String> DEFAULT_PRODICERS_AND_CONSUMERS =
            new HashSet<>(Arrays.asList("application/json", "application/xml"));


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAUL_API_INFO)
                .produces(DEFAULT_PRODICERS_AND_CONSUMERS)
                .consumes(DEFAULT_PRODICERS_AND_CONSUMERS)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();

    }

}

