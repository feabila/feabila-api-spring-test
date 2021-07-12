package com.example.springapitest.controller;

import com.example.springapitest.config.security.TokenService;
import com.example.springapitest.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form) {
        UsernamePasswordAuthenticationToken authLogin = form.convertAuth();
        try {
            Authentication authentication = authenticationManager.authenticate(authLogin);
            String token = tokenService.generateToken(authentication);
            System.out.println(token);
            return ResponseEntity.ok().build();
        } catch (UsernameNotFoundException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
