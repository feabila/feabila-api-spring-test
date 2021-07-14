package com.example.springapitest.config.security;

import com.example.springapitest.model.User;
import com.example.springapitest.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationBearerTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository userRepository;

    public AuthenticationBearerTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        boolean valid = tokenService.isValid(token);

        if (valid) {
            authenticateUser(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        Long userID = tokenService.getUserID(token);
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            User userObject = user.get();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userObject, null, userObject.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String tokenAuthorization = request.getHeader("Authorization");
        if (tokenAuthorization == null || tokenAuthorization.isEmpty() || !tokenAuthorization.startsWith("Bearer ")) {
            return null;
        }
        return tokenAuthorization.substring(7);
    }
}
