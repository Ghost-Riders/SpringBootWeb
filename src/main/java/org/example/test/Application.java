package org.example.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class Application {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("greetings");
		return cacheManager;
	}
}
