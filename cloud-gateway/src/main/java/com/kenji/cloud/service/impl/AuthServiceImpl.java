package com.kenji.cloud.service.impl;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.repository.UserRepository;
import com.kenji.cloud.service.AuthService;
import com.kenji.cloud.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, @Qualifier("UserDetailsService") UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        final String username = user.getUsername();
        if(userRepository.findByUsername(username) == null)
            return null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String passowrd = user.getPassword();
        user.setPassword(encoder.encode(passowrd));
        user.setLastPasswordResetDate(new Date());
        return userRepository.save(user);
    }

//    @Override
//    public String login(User user) {
//        User currentUser = userRepository.findUserAndRoleByUsername(user.getUsername());
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//        user.setId(currentUser.getId());
//        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
//        User u = userRepository.findByUsername(user.getUsername());
//        user.setUserRoles(u.getUserRoles());
//        final String token = jwtTokenUtil.generateToken(userDetails);
//        return token;
//    }

    @Override
    public Map<String, Object> login(User user) {
        User currentUser = userRepository.findUserAndRoleByUsername(user.getUsername());
        if (currentUser == null)
            return null;
        if (!currentUser.getPassword().equals(user.getPassword()))
            return null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        User u = userRepository.findByUsername(user.getUsername());
        user.setUserRoles(u.getUserRoles());
        final String token = jwtTokenUtil.generateToken(userDetails);
        Map<String , Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", currentUser);
        return result;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        if(jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
