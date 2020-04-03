package com.redis.tutorial.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * POJO class to represent user to be created
 */
@Getter
@Setter
public class UserRequestDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private int age;
}
