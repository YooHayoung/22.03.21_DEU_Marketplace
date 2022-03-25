//package com.deu.marketplace.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class WebMvcConfig implements WebMvcConfigurer {
//
////	private final long MAX_AGE_SECS = 3600;
////
////	@Override
////	public void addCorsMappings(CorsRegistry registry) {
////		registry.addMapping("/**")
////				.allowedOrigins("*")
////				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
////				.allowedHeaders("*")
////				.allowCredentials(true)
////				.maxAge(MAX_AGE_SECS);
////	}
//
//	//	// Controller 없이 특정 뷰로 이동. /app 으로 들어오는 요청들은 다 보낸다.
////	// React - SPA
////	@Override
////	public void addViewControllers(ViewControllerRegistry registry) {
////	    registry.addViewController("/app/**").setViewName("forward:/index.html");
////	}
//
//}
