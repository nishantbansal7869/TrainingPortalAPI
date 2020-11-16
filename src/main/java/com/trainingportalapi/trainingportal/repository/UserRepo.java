package com.trainingportalapi.trainingportal.repository;

import com.trainingportalapi.trainingportal.domain.User;
import com.trainingportalapi.trainingportal.exceptions.TPAuthException;

public interface UserRepo {
    Integer registerUser(String firstName, String lastName, String email, String password) throws TPAuthException;
    User findByEmailAndPassword(String email, String password);
    User findByUserID(Integer userID);
    Integer getEmailCount(String email);
}
