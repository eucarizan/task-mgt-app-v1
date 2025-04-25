package dev.nj.tms.service.impl;

import dev.nj.tms.model.AppUser;
import dev.nj.tms.model.SecurityUser;
import dev.nj.tms.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository userRepository;

    public UserDetailsServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository
                .findByEmail(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new SecurityUser(user);
    }
}
