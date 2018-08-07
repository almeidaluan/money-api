package com.apimoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApimoneyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApimoneyApiApplication.class, args);
	}
}

// @Bean
// public CacheManager cacheManager() {
// /**
// * Cache do spring que tem que ser tirado manualmente com cache evict no save
// * pra listar dnv
// */
// // return new ConcurrentMapCacheManager();
// CacheBuilder<Object, Object> builder =
// CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5,
// TimeUnit.MINUTES);
// GuavaCacheManager manager = new GuavaCacheManager();
// manager.setCacheBuilder(builder);
// return manager;
// }
// }
