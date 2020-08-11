package ru.mitusov.configuration;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.logging.Logger;

@Configuration
@ComponentScan(basePackages = "ru.mitusov")
@EnableWebMvc
@PropertySource("classpath:persistence-mysql.properties")
public class AppConfiguration {

    @Autowired
    private Environment environment;

    private Logger logger =
            Logger.getLogger(getClass().getName());

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setSuffix(".jsp");
        viewResolver.setPrefix("/WEB-INF/view/");

        return viewResolver;
    }

    @Bean
    public DataSource securityDataSource() {

        ComboPooledDataSource pooledDataSource =
                new ComboPooledDataSource();
        try {
            pooledDataSource.setDriverClass(environment.getProperty("jdbc.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        logger.info("==>> jdbc.driver = " + environment.getProperty("jdbc.driver"));
        logger.info("==>> dbc.user = " + environment.getProperty("jdbc.user"));

        pooledDataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        pooledDataSource.setUser(environment.getProperty("jdbc.user"));
        pooledDataSource.setPassword(environment.getProperty("jdbc.password"));

        pooledDataSource.setInitialPoolSize(
                getIntProperty("connection.pool.initialPoolSize"));
        pooledDataSource.setMinPoolSize(
                getIntProperty("connection.pool.minPoolSize"));
        pooledDataSource.setMaxPoolSize(
                getIntProperty("connection.pool.maxPoolSize"));
        pooledDataSource.setMaxIdleTime(
                getIntProperty("connection.pool.maxIdleTime"));


        return pooledDataSource;
    }

    private int getIntProperty(String propVal) {
        Objects.requireNonNull(propVal, "Some of the property for configuration of PoolSize is null");
        return Integer.parseInt(environment.getProperty(propVal));
    }
}
