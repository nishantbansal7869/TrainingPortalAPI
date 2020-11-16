package com.trainingportalapi.trainingportal.services;

import com.trainingportalapi.trainingportal.domain.User;
import com.trainingportalapi.trainingportal.exceptions.TPAuthException;
import com.trainingportalapi.trainingportal.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws TPAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if (null != email) {
            email = email.toLowerCase();
        }

        if(!pattern.matcher(email).matches()){
            throw new TPAuthException("Invalid email format");
        }

        Integer count = userRepo.getEmailCount(email);
        if (count > 0) {
            throw new TPAuthException("Email already registered");
        }

        Integer userID = userRepo.registerUser(firstName, lastName, email, password);
        return userRepo.findByUserID(userID);
    }

    @Override
    public User loginUser(String email, String password) throws TPAuthException {
        if (null != email){
            email = email.toLowerCase();
        }
        return userRepo.findByEmailAndPassword(email, password);
    }
}
