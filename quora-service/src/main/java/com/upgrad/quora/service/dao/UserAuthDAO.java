package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserAuthDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public UserAuthEntity create(final UserAuthEntity newToken){
        entityManager.persist(newToken);
        return newToken;
    }
}
