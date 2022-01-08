package io.github.zhaord.dynamicapi.service;

import io.github.zhaord.dynamicapi.annotation.DynamicApi;

/**
 * @author zhaord
 */
@DynamicApi
public class UserService {
    public String getHello(String name){
        return "hello,"+name;
    }

    public String sayHello(String name){
        return "say hello,"+name;
    }

    public String saveName(String name){
        return "save,"+name;
    }

    public String updateName(String name){
        return "update,"+name;
    }

    public String removeName(String name){
        return "remove,"+name;
    }
}
