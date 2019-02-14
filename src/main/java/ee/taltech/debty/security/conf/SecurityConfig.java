package ee.taltech.debty.security.conf;

import ee.taltech.debty.security.handlers.ApiAccessDeniedHandler;
import ee.taltech.debty.security.handlers.ApiAuthFailureHandler;
import ee.taltech.debty.security.handlers.ApiAuthSuccessHandler;
import ee.taltech.debty.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@Order(1)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final ApiAuthFailureHandler failureHandler;
    private final ApiAuthSuccessHandler successHandler;
    private final ApiAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, ApiAuthFailureHandler failureHandler,
                          ApiAuthSuccessHandler successHandler, ApiAccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration/**").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .formLogin().permitAll()
                .failureHandler(failureHandler)
                .successHandler(successHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}