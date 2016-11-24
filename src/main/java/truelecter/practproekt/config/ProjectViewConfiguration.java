package truelecter.practproekt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ProjectViewConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("greeting");
        registry.addViewController("/user").setViewName("greeting");
        registry.addViewController("/method").setViewName("greeting");
        //registry.addViewController("/").setViewName("index");
        registry.addViewController("/info").setViewName("index");
        
    }

}