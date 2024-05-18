package com.slip.user.util;


import com.slip.user.Models.User;
import com.slip.user.dto.LoginRequestDto;
import com.slip.user.security.UserPrincipal;
import com.slip.user.service.EmailService;
import com.slip.user.service.UserService;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.apache.commons.lang3.StringUtils;



import java.util.Optional;

@UtilityClass
public class AppUtils {

    public static String getUserRef() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(UserPrincipal.class::cast)
                .map(UserPrincipal::getUserRef)
                .orElseThrow(() -> new RuntimeException("User reference not found"));
    }
    public static String getUserEmail() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(UserPrincipal.class::cast)
                .map(UserPrincipal::getEmail)
                .orElseThrow(() -> new RuntimeException("User reference not found"));
    }


}
