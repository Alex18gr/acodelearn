package com.alexc.acodelearn.authorizationserver.service;

import com.alexc.acodelearn.authorizationserver.entity.MyUserPrincipal;
import com.alexc.acodelearn.authorizationserver.entity.User;
import com.alexc.acodelearn.authorizationserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return new MyUserPrincipal(user);
    }
}
