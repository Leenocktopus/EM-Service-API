package com.leandoer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.leandoer.security",
		entityManagerFactoryRef = "securityEntityManager",
		transactionManagerRef = "securityTransactionManager"
)
public class SecurityDBConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.securitydatasource")
	public DataSource securityDataSource() {
		return DataSourceBuilder.create()
				.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean securityEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(securityDataSource());
		em.setPackagesToScan("com.leandoer.security");
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		em.setJpaPropertyMap(properties);
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		return em;
	}

	@Bean
	public PlatformTransactionManager securityTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(securityEntityManager().getObject());
		return transactionManager;
	}

}
