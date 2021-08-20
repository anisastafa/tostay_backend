package com.ubt.app.security;

import com.ubt.app.jwt.JwtTokenVerifier;
import com.ubt.app.jwt.JwtUsernameAndPasswordAuthAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ubt.app.security.ApplicationUserPermission.USER_READ;
import static com.ubt.app.security.ApplicationUserRole.HOST;
import static com.ubt.app.security.ApplicationUserRole.USER;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("myUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and() // check if user exist in database and create a token for specific user
                .addFilter(new JwtUsernameAndPasswordAuthAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/login", "/createHost", "/createUser", "/getAllApartments").permitAll()
//                .antMatchers("/apartments/**", "/hosts/**", "/bookings/**", "/users/**"
//                ).authenticated()
                .antMatchers("/users/**", "/bookings/**").hasRole(USER.name())
                .antMatchers("/hosts/**", "/apartments/**").hasRole(HOST.name()).anyRequest().authenticated()
//                .antMatchers("users/**").hasAnyRole(USER.name(), HOST.name()).anyRequest().authenticated()
//                .antMatchers(HttpMethod.GET,"/users/**").hasAuthority(USER_READ.getPermission())
//                .antMatchers(HttpMethod.GET,"/hosts/**").hasAuthority(HOST_READ.getPermission())
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint);
    }
}
