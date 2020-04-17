package com.redis.tutorial.controller;

import com.redis.tutorial.entity.User;
import com.redis.tutorial.payload.UserRequestDto;
import com.redis.tutorial.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequestDto requestDto,
                                           HttpServletRequest httpServletRequest) throws URISyntaxException {
        int userId = userService.createUser(requestDto);
        return ResponseEntity.created(new URI(httpServletRequest.getContextPath() + "/users/"
                + userId)).build();
    }

    /**
     * It fetches a user and cached it only if the age of user is more than and equal to 18
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/{userIdentifier}")
    public User getUser(@PathVariable("userIdentifier") int userId) {
        LOG.info("Getting user with ID " + userId);
        return userService.getUserById(userId);
    }

    /**
     * fetch all user
     *
     * @return
     */
    @GetMapping
    public List<User> getUser() {
        LOG.info("Getting all users");
        return userService.findAllUsers();
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") Integer userId, @RequestBody User user) {
        LOG.info("updating user for id : " + userId);
        userService.updateUser(userId, user);
    }
}
