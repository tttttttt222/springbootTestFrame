package com.examples.demo.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 项目名称:auxiliary-parent 描述: 创建人:ryw 创建时间:2019/1/21
 */
@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {
	@Value("${redis.clusterName}")
	private String clusterName;

	@Value("${redis.minIdle}")
	private int redisMinIdle;
	@Value("${redis.maxIdle}")
	private int redisMaxIdle;
	@Value("${redis.maxTotal}")
	private int redisMaxTotal;
	@Value("${redis.testOnBorrow}")
	private String redisTestOnBorrow;

	@Value("${redis1.host}")
	private String redisHost1;
	@Value("${redis1.port}")
	private int redisPort1;

	@Value("${redis2.host}")
	private String redisHost2;
	@Value("${redis2.port}")
	private int redisPort2;

	@Value("${redis3.host}")
	private String redisHost3;
	@Value("${redis3.port}")
	private int redisPort3;


	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMinIdle(this.redisMinIdle);
		poolConfig.setMaxIdle(this.redisMaxIdle);
		poolConfig.setMaxTotal(this.redisMaxTotal);
		poolConfig.setTestOnBorrow(Boolean.valueOf(this.redisTestOnBorrow));
		return poolConfig;
	}


	@Bean
	public RedisSentinelConfiguration redisSentinelConfiguration() {
		RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
		redisSentinelConfiguration.setMaster(this.clusterName);
		Set<RedisNode> sentinels = new HashSet<>();
		sentinels.add(new RedisNode(this.redisHost1, this.redisPort1));
		sentinels.add(new RedisNode(this.redisHost2, this.redisPort2));
		sentinels.add(new RedisNode(this.redisHost3, this.redisPort3));
		redisSentinelConfiguration.setSentinels(sentinels);
		return redisSentinelConfiguration;
	}



	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory(this.redisSentinelConfiguration());
		connectionFactory.setPoolConfig(this.jedisPoolConfig());
		return connectionFactory;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate() {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(this.jedisConnectionFactory());
		return stringRedisTemplate;
	}



}
