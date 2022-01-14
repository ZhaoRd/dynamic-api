package io.github.zhaord.dynamicapi.service;

import io.github.zhaord.dynamicapi.annotation.DynamicApi;

/**
 * @author zhaord
 */
@DynamicApi
public class DemoService {

    public String getDemo(){
        return "demo";
    }

}
