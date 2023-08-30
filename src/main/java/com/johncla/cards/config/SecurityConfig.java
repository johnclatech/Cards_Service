package com.johncla.cards.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public interface SecurityConfig {
    void configure(AuthenticationManagerBuilder auth) throws Exception;

    SecurityFilterChain configure(HttpSecurity http) throws Exception;
}
