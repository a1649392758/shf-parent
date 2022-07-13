package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName WebSecurityConfig
 * @Description TODO
 * @Author 郑帅
 * @DATE 2022/7/11 9:32
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //@Override
    //protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.inMemoryAuthentication()
    //            .withUser("admin")
    //            .password(new BCryptPasswordEncoder().encode("123456"))
    //            .roles("admin","manager");
    //    auth.inMemoryAuthentication()
    //            .withUser("admin123")
    //            .password(new BCryptPasswordEncoder().encode("123456"))
    //            .roles("");
    //}

    /**
     * 必须指定加密方式，上下加密方式要一致
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //http.headers().frameOptions().sameOrigin();
        //允许iframe嵌套显示
        http.headers().frameOptions().disable();
        //登录设置
        http.authorizeRequests()
                .antMatchers("/static/**","/loginPage").permitAll()
                .anyRequest().authenticated();
        http.formLogin().loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/loginPage")
                .and()
                .csrf().disable();
    }

}
