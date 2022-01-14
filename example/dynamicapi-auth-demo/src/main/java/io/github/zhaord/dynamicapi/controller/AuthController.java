package io.github.zhaord.dynamicapi.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaord
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody LoginModel loginModel){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginModel.getUsername(), loginModel.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Object principal = authentication.getPrincipal();
        return "";
    }

    @PostMapping("/logout")
    public void logout(){
        SecurityContextHolder.clearContext();
    }

    @Data
    public static class LoginModel{
        private String username;
        private String password;
    }
}
