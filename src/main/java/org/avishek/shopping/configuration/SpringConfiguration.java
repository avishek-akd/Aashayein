/**
 * @Project_Name shopping
 * © @Author avishekdas
 * package org.avishek.shopping.configuration;
 * @File_Name SpringConfiguration.java
 * @Created_Date 05-Oct-2018
 * Modified by @author avishekdas last on 2018-10-05 23:58:33
 */

package org.avishek.shopping.configuration;

import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.avishek.shopping.controller",
		"org.avishek.shopping.dao",
		"org.avishek.shopping.service"})
public class SpringConfiguration implements WebMvcConfigurer{
	
	//MySql DATABASE connection information
	private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/";
	private final static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private final static String DATABASE_USERNAME = "root";
	private final static String DATABASE_PASSWORD = "";
	
	//Hibernate Property
	private final static String HIBERNATE_DIALECT = "org.hibernate.dialect.MySQLDialect";
	
	//Creating ViewResolver Bean
	@Bean
	public ViewResolver getResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	//Creating DataSource Bean
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(DATABASE_URL);
		dataSource.setDriverClassName(DATABASE_DRIVER);
		dataSource.setUsername(DATABASE_USERNAME);
		dataSource.setPassword(DATABASE_PASSWORD);
		
		return dataSource;
	}
	
	//Creating SessionFactory Bean
	@Bean
	public LocalSessionFactoryBean getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan("org.avishek.shopping.dao");
		
		//Setting Hibernate Properties
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
		hibernateProperties.put("hibernate.show_sql", "true");
		//hibernateProperties.put("hibernate.format_sql", "true");
		//hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
		
		sessionFactory.setHibernateProperties(hibernateProperties);
		
		return sessionFactory;	
	}
	
	//Creating HibernateTemplate Bean
	@Bean
	public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory) {
		HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
		
		return hibernateTemplate;
	}
	
	//Creating MessageSource Bean
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		
		return messageSource;
	}
	
	//Creating LocaleResolver Bean
	@Bean
	public LocaleResolver localeResolver(){
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("hi"));
		
		return localeResolver;
	}
	
	//Adding LocaleResolver 
	public void addInterceptors(InterceptorRegistry registry){
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("siteLanguage");
		registry.addInterceptor(localeChangeInterceptor);
	}
}