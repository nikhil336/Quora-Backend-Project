package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDAO;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    public UserEntity signUp(UserEntity userEntity) throws SignUpRestrictedException {

        if(userDAO.checkEmail(userEntity.getEmailAddress())) {
            if(userDAO.checkUserName(userEntity.getUserName())) {
                final String[] cryPasswordAndSalt = passwordCryptographyProvider.encrypt(userEntity.getPassword());
                userEntity.setPassword(cryPasswordAndSalt[1]);
                userEntity.setSalt(cryPasswordAndSalt[0]);

                userEntity = userDAO.createUser(userEntity);
            }
            else {
                throw new SignUpRestrictedException("'SGR-001","Try any other Username, this Username has already been taken");
            }
        }
        else {
            throw new SignUpRestrictedException("SGR-002","This user has already been registered, try with any other emailId");
        }
        return userEntity;
    }

}
