package com.examples.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import lombok.Getter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2018/12/25
 */
@Configuration
@PropertySource("classpath:datasource.properties")
@MapperScan(basePackages = DataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory")
@Getter
public class DataSourceConfig {


	static final String PACKAGE = "com.examples.demo.dao.mapper";

	static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

	@Value("${ds.jdbc.driverClassName}")
	private String driverClassName;
	@Value("${ds.jdbc.url}")
	private String url;
	@Value("${ds.jdbc.username}")
	private String username;
	@Value("${ds.jdbc.password}")
	private String password;
	@Value("${ds.jdbc.initialSize}")
	private int initialSize;
	@Value("${ds.jdbc.minIdle}")
	private int minIdle;
	@Value("${ds.jdbc.maxActive}")
	private int maxActive;
	@Value("${ds.jdbc.maxWait}")
	private int maxWait;
	@Value("${ds.jdbc.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${ds.jdbc.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	@Value("${ds.jdbc.testWhileIdle}")
	private Boolean testWhileIdle;
	@Value("${ds.jdbc.testOnBorrow}")
	private Boolean testOnBorrow;
	@Value("${ds.jdbc.testOnReturn}")
	private Boolean testOnReturn;
	@Value("${ds.jdbc.poolPreparedStatements}")
	private Boolean poolPreparedStatements;
	@Value("${ds.jdbc.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	@Value("${ds.jdbc.defaultAutoCommit}")
	private Boolean defaultAutoCommit;
	@Value("${ds.jdbc.validationQuery}")
	private String validationQuery;
	@Value("${ds.jdbc.filters}")
	private String filters;


	@Bean
	public DataSource dataSource() throws Exception {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(this.getDriverClassName());
		dataSource.setUrl(this.getUrl());
		dataSource.setUsername(this.getUsername());
		dataSource.setPassword(this.getPassword());
		dataSource.setInitialSize(this.getInitialSize());
		dataSource.setMinIdle(this.getMinIdle());
		dataSource.setMaxActive(this.getMaxActive());
		dataSource.setMaxWait(this.getMaxWait());
		dataSource.setTimeBetweenEvictionRunsMillis(this.getTimeBetweenEvictionRunsMillis());
		dataSource.setMinEvictableIdleTimeMillis(this.getMinEvictableIdleTimeMillis());
		dataSource.setTestWhileIdle(this.getTestWhileIdle());
		dataSource.setTestOnBorrow(this.getTestOnBorrow());
		dataSource.setTestOnReturn(this.getTestOnReturn());
		dataSource.setPoolPreparedStatements(this.getPoolPreparedStatements());
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(this.getMaxPoolPreparedStatementPerConnectionSize());
		dataSource.setDefaultAutoCommit(this.getDefaultAutoCommit());
		dataSource.setValidationQuery(this.getValidationQuery());
		dataSource.setFilters(this.getFilters());
		return dataSource;
	}

	@Bean
	@Primary
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}



}
