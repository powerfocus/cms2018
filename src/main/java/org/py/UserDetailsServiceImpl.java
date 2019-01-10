package org.py;

import org.py.mapper.UserMapper;
import org.py.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if(null == user)
            throw new UsernameNotFoundException("user " + username + " not found.");
        List<String> roles = new ArrayList<>(Arrays.asList(user.getRole().split(",")));
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(it -> authorities.add(new SimpleGrantedAuthority(it)));
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
        return userDetails;
    }
}
