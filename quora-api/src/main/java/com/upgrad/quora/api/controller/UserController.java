package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.business.UserService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/user/signup",method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(final SignupUserRequest signupUserRequest) throws SignUpRestrictedException {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(signupUserRequest.getFirstName());
        userEntity.setLastName(signupUserRequest.getLastName());
        userEntity.setContactNumber(signupUserRequest.getContactNumber());
        userEntity.setUserName(signupUserRequest.getUserName());
        userEntity.setEmailAddress(signupUserRequest.getEmailAddress());
        userEntity.setPassword(signupUserRequest.getPassword());
        userEntity.setCountry(signupUserRequest.getCountry());
        userEntity.setAboutMe(signupUserRequest.getAboutMe());
        userEntity.setDob(signupUserRequest.getDob());
        userEntity.setRole("nonadmin");
        userEntity.setUuid(UUID.randomUUID().toString());
        String message;
        String code;
        try {
            userEntity = userService.signUp(userEntity);
            message = "USER SUCCESSFULLY REGISTERED";
            code = userEntity.getUuid();
        }
        catch (SignUpRestrictedException e) {
            message = e.getErrorMessage();
            code = e.getCode();
        }
        SignupUserResponse response = new SignupUserResponse();
        response.setId(code);
        response.setStatus(message);
        System.out.println(userEntity);
        return new ResponseEntity<SignupUserResponse>(response,HttpStatus.OK);

    }
}
