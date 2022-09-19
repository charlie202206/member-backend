package com.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJpaAuditing     // JPA Auditing을 활성화 하기 위한 어노테이션  
@SpringBootApplication
// @EnableBinding(KafkaProcessor.class)
public class MemberApplication {
	protected static ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}

	//  @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// 	return new WebMvcConfigurer() {
	// 		@Override
	// 		public void addCorsMappings(CorsRegistry registry) {
	// 			registry.addMapping("/**")
	// 							.allowedOrigins("*");
	// 							//.allowedMethods("GET","POST","PUT","PATCH","DELTE")
	// 							//.allowCredentials(false).maxAge(3600);
	// 			//registry.addMapping("/**").allowedOrigins("http://front-server.com");
	// 		}
	// 	};
	// }
}
