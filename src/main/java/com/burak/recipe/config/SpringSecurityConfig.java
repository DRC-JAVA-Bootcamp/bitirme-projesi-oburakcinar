package com.burak.recipe.config;

import com.burak.recipe.repository.RoleRepository;
import com.burak.recipe.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //member service is used for authentication
        auth
                .userDetailsService(memberService)
                .passwordEncoder(passwordEncoder);

        //creating an in memory admin account
        auth.inMemoryAuthentication()  //admin password username
                .withUser("admin")
                .password(passwordEncoder.encode("1"))
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic()
                .and()

                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/api/member/**").hasRole("MEMBER")
                .antMatchers(HttpMethod.DELETE, "/api/member/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/recipes/**").hasRole("MEMBER")
                .antMatchers(HttpMethod.DELETE, "/api/recipes/**").hasRole("MEMBER")
                .antMatchers(HttpMethod.POST, "/api/comments/**").hasRole("MEMBER")
                .antMatchers(HttpMethod.DELETE, "/api/comments/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/likes/**").hasRole("MEMBER")
                .antMatchers(HttpMethod.DELETE, "/api/likes/**").hasRole("MEMBER")
                .antMatchers(HttpMethod.POST, "/api/rates/**").hasRole("MEMBER")
                .and()
                .csrf().disable()
                .formLogin().disable();

    }





}


