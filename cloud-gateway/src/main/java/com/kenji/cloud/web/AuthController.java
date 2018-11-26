package com.kenji.cloud.web;

import com.kenji.cloud.entity.User;
import com.kenji.cloud.service.AuthService;
import com.kenji.cloud.vo.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(("/auth"))
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        final String token = authService.login(user);
        System.out.println(user);
        Map<String,Object> result = new HashMap<>();
        result.put("currentAuthority", user.getRoles().get(0).getName());
        result.put("token",token);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null)
            return ResponseEntity.badRequest().body(null);
        else
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

}
