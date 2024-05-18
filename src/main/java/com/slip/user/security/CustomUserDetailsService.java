package com.slip.user.security;

import com.slip.user.Models.User;
import com.slip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userDto =
                userService
                        .getUserByEmail(email);
        return new UserPrincipal(
                userDto.getRef().toString(),
                userDto.getEmail(),
                userDto.getPassword());
    }


}
