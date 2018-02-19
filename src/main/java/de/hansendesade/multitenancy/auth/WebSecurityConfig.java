package de.hansendesade.multitenancy.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtSecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${security.basic.enabled}")
  private boolean securityEnabled;

  @Autowired
  private JwtSecurityProperties jwtSecurityProperties;

  @Autowired
  private UserDetailsService userDetailsService;

  @Bean(name = "passwordEncoder")
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    final HttpSecurity httpSecurity = http.csrf().disable();
    final SessionManagementConfigurer<HttpSecurity> sessionCreationPolicy = httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    if (securityEnabled) {
      sessionCreationPolicy.and().authorizeRequests()
        .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**").authenticated()
        .antMatchers("/prometheus").hasAnyRole("SUPERUSER").anyRequest().authenticated().and()
        .httpBasic().and()
        .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), jwtSecurityProperties.getSecret(), jwtSecurityProperties.getExpiration()), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JwtAuthorizationFilter(userDetailsService, jwtSecurityProperties.getSecret()), BasicAuthenticationFilter.class);
    } else {
      sessionCreationPolicy.and().authorizeRequests().anyRequest().permitAll().and().httpBasic();
    }
    // H2 web console
    http.headers().frameOptions().disable();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
