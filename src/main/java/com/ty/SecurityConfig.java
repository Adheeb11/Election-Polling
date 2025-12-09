package com.ty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        PasswordEncoder encoder = passwordEncoder();

        var admin = User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        var user1 = User.withUsername("user1")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        var user2 = User.withUsername("user2")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        var user3 = User.withUsername("user3")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        var user4 = User.withUsername("user4")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        var user5 = User.withUsername("user5")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(
                admin, user1, user2, user3, user4, user5
        );
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/**/*.css").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/default", true)
                        .permitAll()
                )

                .logout(Customizer.withDefaults());

        return http.build();
    }
}
