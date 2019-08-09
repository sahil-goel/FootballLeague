package com.sapient.football.league.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//https://github.com/samoyedic/swagger2-demo/blob/master/src/main/java/com/test/api/Swagger2.java
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket swagger() {
		return 
			new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(createApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.sapient.football.league.rest"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo createApiInfo() {
		return new ApiInfoBuilder()
					.title("Football League Application")
					.description("\"An application to find standings of a team playing league football match using country name, league name and team name.\"")
					.build();
	}

	@Controller
	public class HomeController {
		@RequestMapping(value = "/")
		public String index() {
			return "/apis";
		}
		
		@RequestMapping("/apis")
		public String home() {
			return "redirect:/swagger-ui.html";
		}
	}
}
