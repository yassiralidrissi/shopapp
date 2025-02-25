package com.project.shop.config.security;

import com.project.shop.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomSecurityChecker {

    private static final String ADMIN_EMAIL = "admin@admin.com";

    public boolean isAdminEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {

            UserDetails userDetails = (User) authentication.getPrincipal();

            return ADMIN_EMAIL.equals(userDetails.getUsername());
        }

        return false;

    }

}
