package com.example.RydeProject_AuthService.filters;

import com.example.RydeProject_AuthService.services.JwtService;
import com.example.RydeProject_AuthService.services.UserDetailsServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.List;

//OncePerRequestFilter -> executes filter once for every request
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtService jwtService;
//    private RequestMatcher uriMatcher;


    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/v1/auth/signin",
            "/api/v1/auth/signup"
//            "/api/v1/auth/validate"
    );

//    PathPatternParser parser = new PathPatternParser();
//    PathPatternRequestMatcher uriMatcher =
//            new PathPatternRequestMatcher(parser.parse("/api/v1/auth/validate/**"), HttpMethod.POST);
    RequestMatcher uriMatcher = request ->
            request.getMethod().equalsIgnoreCase("POST") &&
                    request.getRequestURI().startsWith("/api/v1/auth/validate");

    public JwtAuthFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;


        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("JwtToken")) token = cookie.getValue();
            }
        }

        if(token == null){
            //user has not provided any JWT token hence request shuld not go forward

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        System.out.println("Incomming Token : " +token);
        String email = jwtService.extractEmail(token);

        if(email != null){

            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);

            if(jwtService.validateToken(token , userDetails.getUsername())){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails , null);

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }


        System.out.println("Forwarding the request");
        filterChain.doFilter(request , response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        String method = request.getMethod();

        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith)
                && HttpMethod.POST.matches(method);
    }

//        @Override
//        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//            String path = request.getServletPath();
//            return path.startsWith("/api/v1/auth/signup") || path.startsWith("/api/v1/auth/signin");
//        }


    //BY SANKET

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
////            RequestMatcher matcher = new NegatedRequestMatcher(uriMatcher);
//        return new uriMatcher.matches(request);
//    }
}
