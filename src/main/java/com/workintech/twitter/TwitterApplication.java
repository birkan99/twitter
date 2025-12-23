package com.workintech.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TwitterApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx =SpringApplication.run(TwitterApplication.class, args);

        for(String instanceName: ctx.getBeanDefinitionNames()){
            System.out.println(instanceName);
        }
	}

}
