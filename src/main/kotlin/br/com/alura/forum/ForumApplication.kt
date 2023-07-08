        package br.com.alura.forum

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import java.util.concurrent.TimeUnit

@SpringBootApplication
@EnableCaching
class ForumApplication : CachingConfigurerSupport(){
	@Bean
	override fun cacheManager(): CacheManager {
		val cacheManager = CaffeineCacheManager()
		cacheManager.setCaffeine(caffeineCacheBuilder())
		return cacheManager
	}

	private fun caffeineCacheBuilder(): Caffeine<Any, Any> {
		return Caffeine.newBuilder()
			.expireAfterWrite(5, TimeUnit.MINUTES)
	}
}

fun main(args: Array<String>) {
	runApplication<ForumApplication>(*args)
}
