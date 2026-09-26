package com.sen.springSecuirtySection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
       // httpSecurity.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure());
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests(
                (request) ->
                        request.requestMatchers("/myAccount").authenticated()
                                .requestMatchers("/notices","/error", "/api/customers/register", "/api/customers/allUsers", "/api/customers/{id}").permitAll());
        httpSecurity.formLogin(withDefaults());
        httpSecurity.httpBasic(hsbc-> hsbc.disable());
        return httpSecurity.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("jobportal")
                        .password("{noop}user@7213").authorities("read").build();
        UserDetails admin =
                User.withUsername("jobportaladmin")
                        .password("{bcrypt}$2a$12$gcRthxnLXjZorpnaQin.2u1F.3meWxc7VxO3nglsbpWy79f60OhIa").authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
        Im memory
    }*/

    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource ) {
        return new JdbcUserDetailsManager(dataSource);
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
