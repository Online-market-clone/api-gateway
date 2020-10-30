package com.emart.proxyservice.config;

import com.emart.proxyservice.service.ServiceDefinitionsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Autowired
	private ServiceDefinitionsContext definitionContext;

	@Bean
	public RestTemplate configureTemplate() {
		return new RestTemplate();
	}

	@Primary
	@Bean
	@Lazy
	public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider,
			RestTemplate temp) {
		return () -> {
			List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
			resources.clear();
			resources.addAll(definitionContext.getSwaggerDefinitions());
			return resources;
		};
	}
}
