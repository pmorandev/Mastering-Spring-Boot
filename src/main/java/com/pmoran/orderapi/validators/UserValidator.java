package com.pmoran.orderapi.validators;

import com.pmoran.orderapi.entity.User;
import com.pmoran.orderapi.exceptions.ValidateServiceException;

public class UserValidator {

    public static void save(User user){
        if (user.getUserName() == null || user.getUserName().isEmpty()){
            throw new ValidateServiceException("Username is required.");
        }

        if (user.getUserName().length() > 30){
            throw new ValidateServiceException("Username is too long (max 30).");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()){
            throw new ValidateServiceException("Password is required.");
        }

        if (user.getPassword().length() > 30){
            throw new ValidateServiceException("Password is too long (max 30).");
        }
    }

}
