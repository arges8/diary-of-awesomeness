package com.arges.diaryofawesomeness.security;

import com.arges.diaryofawesomeness.model.AuthenticationRequest;
import com.arges.diaryofawesomeness.model.AuthenticationResponse;
import com.arges.diaryofawesomeness.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserRepositoryUserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserRepositoryUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
