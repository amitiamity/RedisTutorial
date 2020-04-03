package com.redis.tutorial.service;

import com.redis.tutorial.entity.User;
import com.redis.tutorial.payload.UserRequestDto;

/**
 * An interface to provide user related services
 */
public interface IUserService {
    /**
     * this creates an user and provides the unique id of the newly created resource
     *
     * @param user
     * @return userId
     */
    public int createUser(UserRequestDto user);

    /**
     * provides the user by id
     *
     * @param id id of requested User
     * @return User
     */
    public User getUserById(int id);
}
