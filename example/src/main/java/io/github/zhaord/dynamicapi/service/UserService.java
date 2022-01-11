package io.github.zhaord.dynamicapi.service;

import io.github.zhaord.dynamicapi.annotation.DynamicApi;

import java.util.Collections;
import java.util.List;

/**
 * @author zhaord
 */
@DynamicApi
public class UserService {
    public List<UserDto> getList(String name){
        return Collections.singletonList(UserDto.builder()
                        .name("zh,"+name)
                .build());
    }

    public UserDto getUser(Long id){
        return UserDto.builder()
                .name("id="+id)
                .build();
    }

    public UserDto insertUser(UserDto user){
        user.setId(1000L);
        return user;
    }

    public UserDto updateUser(UserDto user){
        user.setName(user.getName()+"-update");
        return user;
    }

    public Long removeUser(Long id){
        return id;
    }

}
