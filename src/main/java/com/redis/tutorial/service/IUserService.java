package com.redis.tutorial.service;

import com.redis.tutorial.entity.User;
import com.redis.tutorial.payload.UserRequestDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import java.util.List;

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

    /**
     * It returns all the users
     * @return
     */
    public List<User> findAllUsers();

    /**
     * Update a user
     * @return updated User instance
     */
    @CachePut(value = "users", key = "#userId")
    @CacheEvict(value = "users", keyGenerator = "customKeyGenerator")
   public User updateUser(Integer userId, User user);
}
