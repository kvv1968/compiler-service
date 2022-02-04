package ru.platform.learning.compilerservice.configs;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;


@Configuration
@Slf4j
public class SwaggerConfig {

    @Value("${app.base-url}")
    private String baseUrl;

    @Bean
    public GroupedOpenApi publicUserApi() {
        log.info("BaseUrl {}", baseUrl);
        return GroupedOpenApi.builder()
                .group("Client")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${application-description}")String appDescription,
                                 @Value("${application-version}")String appVersion) {
        return new OpenAPI().info(new Info().title("compiler-service API")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact().name("username")
                                .email("test.avit2020@gmail.com")))
                .servers(new ArrayList<Server>(){{
                    add(new Server().url(baseUrl).description("Dev service"));
                }});
    }
}
