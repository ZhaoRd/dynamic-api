package io.github.zhaord.dynamicapi.core;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author zhao rende
 */
@Data
public class DynamicApiMethodInfo {
    private String methodName;
    private Method method;
    private String verb;
    public DynamicApiMethodInfo(String methodName,Method method){
        this.method=method;
        this.methodName=methodName;
    }
}
