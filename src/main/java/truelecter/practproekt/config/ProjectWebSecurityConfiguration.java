package truelecter.practproekt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@ComponentScan(basePackages = "truelecter.practproekt")
@EnableWebSecurity
public class ProjectWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    public static final String STUDENT = "STUDENT";
    public static final String ADMIN = "ADMIN";
    public static final String METHODIST = "METHODIST";
    
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        //Define some basic users
        authenticationMgr.  inMemoryAuthentication()
                .withUser("test").password("test").authorities("ROLE_" + STUDENT)
            .and()
                .withUser("admin").password("admin").authorities("ROLE_" + ADMIN)
            .and()
                .withUser("method").password("method").authorities("ROLE_" + METHODIST);
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    //Guest login
                    .antMatchers(HttpMethod.GET, "/").permitAll()
                    //Permit only methodist and admin to change disciplines
                    .antMatchers(HttpMethod.POST, "/discipline").hasAnyRole(ADMIN, METHODIST)
                    .antMatchers(HttpMethod.PUT, "/discipline").hasAnyRole(ADMIN, METHODIST)
                    //Test authority
                    .antMatchers(HttpMethod.GET, "/admin").hasRole(ADMIN)
                    .antMatchers(HttpMethod.GET, "/user").hasRole(STUDENT)
                    .antMatchers(HttpMethod.GET, "/method").hasRole(METHODIST)
                    
                .and()
                    .formLogin()
                        .loginPage("/login")                
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .permitAll()
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                .and()
                    .exceptionHandling()
//                    .accessDeniedPage("/403")
                .and()
                    .csrf().disable();
    }
}
