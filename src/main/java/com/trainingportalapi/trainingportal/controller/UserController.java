package com.trainingportalapi.trainingportal.controller;

import com.trainingportalapi.trainingportal.Constants;
import com.trainingportalapi.trainingportal.domain.User;
import com.trainingportalapi.trainingportal.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap){
        String firstName = (String)userMap.get("firstName");
        String lastName = (String)userMap.get("lastName");
        String email = (String)userMap.get("email");
        String password = (String)userMap.get("password");
        User user = userService.registerUser(firstName, lastName, email, password);
        return new ResponseEntity<>(generateWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
        String email = (String)userMap.get("email");
        String password = (String)userMap.get("password");
        User user = userService.loginUser(email, password);
        return new ResponseEntity<>(generateWTToken(user), HttpStatus.OK);
    }

    private Map<String, String> generateWTToken(User user){
        long timeStamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timeStamp))
                .setExpiration(new Date(timeStamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserID())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
