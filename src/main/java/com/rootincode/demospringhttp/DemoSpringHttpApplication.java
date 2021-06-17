package com.rootincode.demospringhttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoSpringHttpApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DemoSpringHttpApplication.class, args);
		RestTemplateDemoService restTemplateDemoService = applicationContext.getBean(RestTemplateDemoService.class);
		restTemplateDemoService.postWithPostForObject();

		WebClientDemoService webClientDemoService = applicationContext.getBean(WebClientDemoService.class);
		webClientDemoService.postSynchronousCall();

	}


}
