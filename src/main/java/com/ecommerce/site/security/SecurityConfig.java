package com.ecommerce.site.security;

import com.ecommerce.site.Repository.UserRepo;
import com.ecommerce.site.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private  UserRepo userRepo;
    private  JwtService jwtService;
    @Autowired
    private  JwtFilter jwtFilter;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    public JwtFilter jwtFilter(){
//        return new JwtFilter(jwtService,userDetailsService());
//    }



    @Bean  //Defines a SecurityFilterChain bean that customizes security settings.
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity
               .csrf(csrf->csrf.disable())
               .authorizeHttpRequests(auth->auth
                       .requestMatchers("/").permitAll()
                       .requestMatchers("/images/**").permitAll()
                       .requestMatchers("/index.html").permitAll()
                       .requestMatchers("/api/auth/**").permitAll()
                       .requestMatchers(HttpMethod.GET,"api/products/**").permitAll()
                       .requestMatchers("/api/auth/change-password").authenticated()
                       .anyRequest().authenticated())
                       .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                       .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        //Configures the application to be stateless (no session storage).
        return httpSecurity.build();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userRepo.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("USER NOT FOUND"));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }




}
