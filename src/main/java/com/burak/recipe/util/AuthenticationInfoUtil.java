package com.burak.recipe.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationInfoUtil {

    public String getAuthenticationUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberUserName;
        if (principal instanceof UserDetails) {
            memberUserName = ((UserDetails)principal).getUsername();
        } else {
            memberUserName = principal.toString();
        }
        return memberUserName;
    }
}
