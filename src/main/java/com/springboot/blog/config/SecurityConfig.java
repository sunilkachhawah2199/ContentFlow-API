package com.springboot.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration // Indicates that this class contains Spring configuration
@EnableMethodSecurity // Enables Spring Security method-level security
public class SecurityConfig {


    // user details service to get user from database
    private UserDetailsService userDetailsService;


    // constructor based dependency injection
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // password encoder
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // authentication manager will use user details service to authenticate user
    @Bean
    // no need to pass explicitly user details service, password encoder. spring will automatically pass it
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean // Marks this method as a bean producer for the Spring context
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configures HTTP security
        http
            // Disables CSRF protection for simplicity
            .csrf(csrf -> csrf.disable())
            // Configures authorization rules
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.GET,"api/posts/**").permitAll() // permit all get request
                    // permit all request for auth
                    .requestMatchers("/api/auth/**").permitAll() // permit all request for auth
                // Requires authentication for all other requests
                .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults()); // Configures HTTP Basic authentication | form will popup for id and password
        return http.build(); // Builds the SecurityFilterChain object
    }

    // in memory user password
//    @Bean
//    public UserDetailsService userDetailsService(){
//        // sunil is admin
//        UserDetails sunil= User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        // anil is simple user
//        UserDetails anil= User.builder()
//                .username("anil")
//                .password(passwordEncoder().encode("anil"))
//                .roles("User")
//                .build();
//
//                return new InMemoryUserDetailsManager(sunil,anil);
//    }
}