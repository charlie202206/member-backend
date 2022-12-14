package com.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Component
public class OpenApiConfig {
  @Bean
  public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
    Info info = new Info().title("SK EcoPointMarcket System API(회원관리)").version(appVersion)
            .description("SK EcoPointMarcket Micro Service API(회원관리)")
            .termsOfService("http://swagger.io/terms/")
            .contact(new Contact().name("member").url("https://www.ecopointmarcket.com/").email("donghu@sk.com"))
            .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

    return new OpenAPI()
            .components(new Components())
            .info(info);
  }
}
