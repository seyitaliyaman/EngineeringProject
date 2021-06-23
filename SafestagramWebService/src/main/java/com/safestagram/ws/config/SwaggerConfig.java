package com.safestagram.ws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${app.name}")
    private  String appName;
    @Value("")
    private String apiContextUrl;
    @Value("${app.url}")
    private String appUrl;
    @Value("${api.version}")
    private String apiVersion;
    @Value("${api.document.description}")
    private String apiDocumentDescription;
    @Value("${app.developer.name}")
    private String appDeveloperName;
    @Value("${app.developer.mail}")
    private String appDeveloperMail;

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(apiContextUrl+".*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData(){
        return new ApiInfoBuilder().title(appName)
                .description(apiDocumentDescription)
                .version(apiVersion)
                .licenseUrl(appUrl+"LICENSE")
                .contact(new Contact(appDeveloperName,appUrl,appDeveloperMail))
                .build();
    }
}
