package io.github.zhaord.dynamicapi.controller;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author zhaord
 */
@RestController
@RequestMapping("userDemo")
@Validated
public class UserDemoController {

    @PostMapping("addUser")
    public void addUser(@RequestBody UserDto input){

    }

    @Data
    public static class UserDto{
        private Long id;
        @NotNull
        private String name;
    }

}
