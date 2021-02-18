package com.pmoran.orderapi.controllers;

import com.pmoran.orderapi.converters.UserConverter;
import com.pmoran.orderapi.dtos.LoginRequestDTO;
import com.pmoran.orderapi.dtos.LoginResponseDTO;
import com.pmoran.orderapi.dtos.SignupDTO;
import com.pmoran.orderapi.dtos.UserDTO;
import com.pmoran.orderapi.entity.User;
import com.pmoran.orderapi.services.UserService;
import com.pmoran.orderapi.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @PostMapping("/signup")
    public ResponseEntity<WrapperResponse<UserDTO>> signup(
            @RequestBody SignupDTO newUser){
        User user = userService
                .save(userConverter.fromSignup(newUser));

        return new WrapperResponse(true, "Success",
                userConverter.fromEntity(user))
                .createResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<WrapperResponse<LoginResponseDTO>> login(
            @RequestBody LoginRequestDTO request){
        return new WrapperResponse(true, "Success",
                userService.login(request)).createResponse();
    }

}
