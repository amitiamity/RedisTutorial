package com.redis.tutorial.service.impl;

import com.redis.tutorial.entity.User;
import com.redis.tutorial.payload.UserRequestDto;
import com.redis.tutorial.repository.UserRepository;
import com.redis.tutorial.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public int createUser(UserRequestDto userRequestDto) {
        User entity = modelMapper.map(userRequestDto, User.class);
        User user = userRepository.save(entity);
        return user.getId();
    }

    @Override
    public User getUserById(int userIid) {
        return userRepository.findById(userIid).get();
    }
}
