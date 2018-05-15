package com.yc.sandfactory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Auther: yangchun
 * @Date: 2017-8-19 10:30
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * 将AuthenticationManager注册为spring bean
   * @throws Exception
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication();
    auth.authenticationProvider(customAuthenticationProvider());
    super.configure(auth);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()

        //.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint()).and()

        // don't create session
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

        .authorizeRequests()

        // 放行带public的路径
        .antMatchers("/public/**").permitAll()

        .antMatchers("/auth/**").authenticated()
        //.antMatchers("/foo/get").access(" hasRole('ROLE_admin') and  hasIpAddress('11.12.109.123')")
        //.antMatchers("/foo/get").permitAll()
        //.access(" hasRole('ROLE_user') ")
        .anyRequest().authenticated();

        //http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  //@Bean
  //public RestLoginSuccessHandler restLoginSuccessHandler() {
  //  RestLoginSuccessHandler successHandler = new RestLoginSuccessHandler();
  //  successHandler.setHmacsha256(macSigner());
  //  return successHandler;
  //}

  /**
   * 放行静态资源
   * @param web
   * @throws Exception
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    // AuthenticationTokenFilter will ignore the below paths

    web.ignoring().antMatchers("/resources/**")
        .antMatchers("/images/**")
        .antMatchers("/script/**")
        .antMatchers("/themes/**")
        .antMatchers("/materialize/**")
        .antMatchers("/view/**")

        // allow anonymous resource requests
        .and()
        .ignoring()
        .antMatchers(
            HttpMethod.GET,
            // "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
        );
  }

  @Bean
  public DaoAuthenticationProvider customAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    //provider.setUserDetailsService(myUserDetailsService());
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  //@Autowired
  //public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  //  auth
  //      .userDetailsService(jwtUserDetailsService)
  //      .passwordEncoder(passwordEncoderBean());
  //}

  //@Bean
  //public UserDetailsService myUserDetailsService() {
  //  return new CustomUserDetailsService();
  //}

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
