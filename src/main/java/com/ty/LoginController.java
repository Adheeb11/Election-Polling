package com.ty;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // redirect based on role after login
    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/user/vote";
    }
}
