package com.cos.costagram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration 
public class WebMvcConfig implements WebMvcConfigurer{ // 내가 web.xml이 되겠다

	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			registry.addResourceHandler("/upload/**") // url 패턴이 이렇게 생겼으면 낚아챔.
			.addResourceLocations("file:///"+uploadFolder) // 물리적인 하드 경로로 바꿔줌
			.setCachePeriod(60*10*6) // 3600 sec
			.resourceChain(true)
			.addResolver(new PathResourceResolver());
		}
}
