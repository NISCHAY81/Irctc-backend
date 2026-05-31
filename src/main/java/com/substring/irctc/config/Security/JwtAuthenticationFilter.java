package com.substring.irctc.config.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private  JwtHelper jwtHelper;
    private CustomUserDetailService userDetailsService;

    public JwtAuthenticationFilter(
            JwtHelper jwtHelper,
            CustomUserDetailService userDetailsService) {

        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // har ek request se pahle

        //Bearer 213534chvsjgkgwwsiww
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extracting the token from the header
           try{
                token = authorizationHeader.substring(7);// Remove "Bearer" prefix
               username = jwtHelper.getUsernameFromToken(token);

               if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                 UserDetails userDetails =  userDetailsService.loadUserByUsername(username);
                 if(jwtHelper.isTokenValid(token, userDetails)) {
                     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                     authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                     SecurityContextHolder.getContext().setAuthentication(authentication);
                 }
               }
           } catch (IllegalArgumentException e) {
               System.out.println("unable to get JWT Token");
               e.printStackTrace();
           } catch (ExpiredJwtException e) {
               System.out.println("expired token");
               e.printStackTrace();
           } catch (MalformedJwtException e) {
               System.out.println("malformed token");
               e.printStackTrace();
           }
           catch (Exception e){
               System.out.println("Invalid token");
               e.printStackTrace();
           }
            // check if the token is valid or not
        } else {
            System.out.println("Invalid token");
        }
        filterChain.doFilter(request, response);
    }
}
