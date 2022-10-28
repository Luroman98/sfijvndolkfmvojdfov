package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")

public class AppConfig {
   private final Environment environment;

   public AppConfig(Environment environment) {
      this.environment = environment;
   }

   @Bean(name = "entityManagerFactory")
   public LocalContainerEntityManagerFactoryBean emf() {
      LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
      emf.setDataSource(dataSource());
      emf.setPackagesToScan("web");
      emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      emf.setJpaProperties(hibernateProperties());
      return emf;
}

   @Bean
   public DataSource dataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));
      dataSource.setUrl(environment.getRequiredProperty("db.url"));
      dataSource.setUsername(environment.getRequiredProperty("db.username"));
      dataSource.setPassword(environment.getRequiredProperty("db.password"));
      return dataSource;
   }

   private Properties hibernateProperties() {
      Properties properties = new Properties();
      properties.put("hibernate.dialect", environment.getRequiredProperty("db.dialect"));
      properties.put("hibernate.show_sql", environment.getRequiredProperty("db.show_sql"));
      properties.put("hibernate.format_sql", environment.getRequiredProperty("db.format_sql"));
      properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("db.hbm2ddl.auto"));
      return properties;
   }

   @Bean(name = "transactionManager")
   public PlatformTransactionManager transactionManager() {
      JpaTransactionManager tm =
              new JpaTransactionManager();
      tm.setEntityManagerFactory(emf().getObject());
      tm.setDataSource(dataSource());
      return tm;
   }

}
