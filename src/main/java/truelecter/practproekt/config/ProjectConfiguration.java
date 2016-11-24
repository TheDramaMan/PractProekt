package truelecter.practproekt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "truelecter.practproekt")
@EnableWebMvc
public class ProjectConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    Environment env;

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setContentType("text/html; charset=UTF-8");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/templates/**").addResourceLocations("/templates/");
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations("classpath:/web/");
        }
    }

     @Override
     public void addViewControllers(ViewControllerRegistry registry) {
         registry.addViewController("/404").setViewName("404");
         registry.addViewController("/403").setViewName("403");
     }

    // @Bean(name = "dataSource")
    // public DataSource getDataSource() {
    // PGPoolingDataSource dataSource = new PGPoolingDataSource();
    // dataSource.setServerName(env.getProperty("db.server.name"));
    // dataSource.setDatabaseName(env.getProperty("db.name"));
    // dataSource.setUser(env.getProperty("db.username"));
    // dataSource.setPassword(env.getProperty("db.password"));
    // dataSource.setMaxConnections(Integer.parseInt(env.getProperty("db.connections")));
    // return dataSource;
    // }
    //
    // @Bean()
    // public JdbcTemplate jdbcTemplate() {
    // return new JdbcTemplate(getDataSource());
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // PasswordEncoder encoder = new BCryptPasswordEncoder();
    // return encoder;
    // }

    // @Bean
    // public RequestContextListener requestContextListener() {
    // return new RequestContextListener();
    // }

}