package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.ErrorResponse;
import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.UserService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommonController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET,path = "user/{userId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable(name = "userId")final String uuid, @RequestHeader("authorization")final String authorization) throws UserNotFoundException, AuthorizationFailedException {

        String message;
        String code;
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        ErrorResponse errorResponse = new ErrorResponse();
        HttpHeaders httpHeaders = new HttpHeaders();

        try {
            UserEntity userEntity = userService.getUser(uuid, authorization);
            userDetailsResponse.setAboutMe(userEntity.getAboutMe());
            userDetailsResponse.setContactNumber(userEntity.getContactNumber());
            userDetailsResponse.setCountry(userEntity.getCountry());
            userDetailsResponse.setDob(userEntity.getDob());
            userDetailsResponse.setEmailAddress(userEntity.getEmailAddress());
            userDetailsResponse.setFirstName(userEntity.getFirstName());
            userDetailsResponse.setLastName(userEntity.getLastName());
            userDetailsResponse.setUserName(userEntity.getUserName());
            message = "User details found.";
            code = userEntity.getUuid();
        }
        catch (UserNotFoundException e) {
            message = e.getMessage();
            code = e.getCode();
        }
        catch (AuthorizationFailedException e) {
            message = e.getMessage();
            code = e.getCode();
        }

        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }
}
