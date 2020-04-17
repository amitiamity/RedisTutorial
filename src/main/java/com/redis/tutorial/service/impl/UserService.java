package com.redis.tutorial.service.impl;

import com.redis.tutorial.entity.User;
import com.redis.tutorial.payload.UserRequestDto;
import com.redis.tutorial.repository.UserRepository;
import com.redis.tutorial.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Integer, User> hashOperations;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, RedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
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

    @Override
    public List<User> findAllUsers() {
        Map<Integer, User> cachedUsers = hashOperations.entries("users");
        if (cachedUsers != null && !cachedUsers.isEmpty()) {
            return cachedUsers.values().stream().collect(Collectors.toList());
        }
        List<User> users = userRepository.findAll();
        Map<Integer, User> toCacheUsers = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        hashOperations.putAll("users", toCacheUsers);
        return users;
    }

    @Override
    public User updateUser(Integer userId, User user) {
        Optional<User> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            User userEntity = userEntityOptional.get();
            if (!StringUtils.isEmpty(user.getName())) {
                userEntity.setName(user.getName());
            }
            if (user.getAge() > 0) {
                userEntity.setAge(user.getAge());
            }
            return userRepository.save(userEntity);
        } else {
            throw new RuntimeException("User Does not exist");
        }
    }
}
