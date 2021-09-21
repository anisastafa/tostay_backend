package com.ubt.app.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubt.app.UserDetails.MyUserDetailsService;
import com.ubt.app.security.ApplicationUserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class JwtUsernameAndPasswordAuthAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.
            getLogger(JwtUsernameAndPasswordAuthAuthenticationFilter.class);

//    @Qualifier("myUserDetailsService")
//    @Autowired
//    private UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationRequest authenticationRequest =
                    new ObjectMapper().readValue(request.getInputStream(),
                            UsernamePasswordAuthenticationRequest.class);

//            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//            System.out.println("userDetails.getAuthorities()-- : "+userDetails.getAuthorities());
//            List<GrantedAuthority> listRole = new ArrayList<>();
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
//                    userDetails.getAuthorities()
            );
            System.out.println("authenticationRequest.getUsername(): " + authenticationRequest.getUsername());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        logger.info("Auth result: {}", authResult);
        logger.info("Authorities: {}", authResult.getAuthorities());
        String key = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();


//
//        response.addHeader("Authorization", "Bearer " + token);
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("authorities", authResult.getAuthorities());
//
//        String token = Jwts.builder()
//                .setSubject(authResult.getName())
//                .setClaims(claims)
////                .claim("authorities", authResult.getAuthorities())
//                .setIssuedAt(Date.valueOf(LocalDate.now().plusWeeks(2)))
//                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
//                .compact();


//        System.out.println("claims: " +claims);
        String tokenAsJson = new JSONObject()
                .put("token", token)
                .toString();


        response.setContentType("application/json");
        response.getWriter().write(tokenAsJson);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
