package com.arvato.core.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

@Configuration
@MapperScan(basePackages = MyBatisConfig.PACKAGE, sqlSessionFactoryRef = "SqlSessionFactory")
public class MyBatisConfig {

	// mysqldao扫描路径
	static final String PACKAGE = "com.arvato.core.mapper";
	// mybatis mapper扫描路径
	static final String MAPPER_LOCATION = "classpath:mybatis/*.xml";
	
	@Primary
	@Bean(name = "datasource")
	@ConfigurationProperties("spring.datasource.druid.one")
	public DataSource DataSource() {
		return DruidDataSourceBuilder.create().build();
	}
	//配置事务管理
	@Bean(name = "transactionManager")
	@Primary
	public DataSourceTransactionManager mysqlTransactionManager() {
		return new DataSourceTransactionManager(DataSource());
	}

	@Bean(name = "SqlSessionFactory")
	@Primary
	public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("datasource") DataSource dataSource)
			throws Exception {
		final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
		
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setGlobalConfig(globalConfig());
		sessionFactory.setPlugins(new Interceptor[]{
                new PaginationInterceptor(),//分页插件配置 
                new OptimisticLockerInterceptor()//乐观锁插件
        });
		sessionFactory.setDataSource(dataSource);
		//如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。
		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(MyBatisConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}
	   /**
     * MyBatis-Plus 全局策略配置
     * @return
     */
    @Bean
	public GlobalConfig globalConfig() {
    	//数据库配置
    	DbConfig dbconfig=new DbConfig();
    	dbconfig.setDbType(DbType.MYSQL);// 数据库类型
    	dbconfig.setIdType(IdType.INPUT);//ID 策略
		// MP 全局配置，更多内容进入类看注释
    	GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setDbConfig(dbconfig);
		return globalConfig;
	}
    
}
