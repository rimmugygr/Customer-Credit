package springboot.customer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories("springboot.customer.repository")
public class JdbcConfig extends AbstractJdbcConfiguration {
    // NamedParameterJdbcOperations is used internally to submit SQL statements to the database
    @Bean
    NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://mysql:3306/customerdb?serverTimezone=UTC&autoReconnect=true&useSSL=false");
        dataSource.setUrl("jdbc:mysql://localhost:3306/customerdb?serverTimezone=UTC&autoReconnect=true&useSSL=false");
        dataSource.setUsername("customer");
        dataSource.setPassword("customer");
        return dataSource;
    }
}
/*
This application is for store customer credits which menage data of customer, product and credit.
Application each customer, credit and product treat as new
Date structure of consumer credit:
		Credit
			- creditName (string, not null, not empty)
		Product
			- productName (string, not null, not empty)
			- value (integer, 0 or larger)
		Customer
			- firstName (string, not null, not empty)
			- surname (string, not null, not empty)
			- pesel (string, not null, not empty)

*/

