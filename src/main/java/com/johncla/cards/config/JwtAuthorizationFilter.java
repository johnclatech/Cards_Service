package com.johncla.cards.config;

import com.johncla.cards.exceptions.UserExceptions;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

        private final JwtTokenProvider jwtTokenProvider;


        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain chain) throws IOException, ServletException {
            String token = jwtTokenProvider.resolveToken(request);
            log.info("Incoming path : " + request.getServletPath());
            log.info(token);
            if(request.getServletPath().equals("/api/v1/auth/register") |
                    request.getServletPath().equals("/login") |
                    request.getServletPath().equals("/register") |
                    request.getServletPath().contains("/swagger-ui/")|
                    request.getServletPath().contains("/api-docs") |
                    request.getServletPath().equals("/favicon.ico")
            ){
                    chain.doFilter(request,response);
                }else{

                if (token != null && jwtTokenProvider.validateToken(token)) {
                    log.info("Toke valid");
                    UsernamePasswordAuthenticationToken auth = jwtTokenProvider.getAuthentication(token);
                    if (auth != null) {
                        try {
                            SecurityContextHolder.getContext().setAuthentication(auth);
                            chain.doFilter(request, response);

                        }catch (Exception e){
                            log.error("Error in Sec_context : " + e.getLocalizedMessage());
                        }
                    }

                }else{
//                    chain.doFilter(request, response);
                    throw new UserExceptions.InvalidTokenException("Invalid Token");
                }
            }
        }

}

