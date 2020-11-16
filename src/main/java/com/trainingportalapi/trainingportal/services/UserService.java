package com.trainingportalapi.trainingportal.services;

import com.trainingportalapi.trainingportal.domain.User;
import com.trainingportalapi.trainingportal.exceptions.TPAuthException;

public interface UserService {
    User registerUser(String firstName, String lastName, String email, String password) throws TPAuthException;
    User loginUser(String email, String password) throws TPAuthException;
}
