package io.github.zhaord.dynamicapi.core;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Data
public class DynamicApiControllerInfo {
    @Getter
    private String beanName;

    @Getter
    private Class<?> beanClass;
    @Getter
    private Map<String, DynamicApiMethodInfo> methodMap;

    public DynamicApiControllerInfo(String beanName,
                                    Class<?> beanClass){
        this.beanName = beanName;
        this.beanClass = beanClass;
        this.methodMap=new HashMap<>();
    }

    public String getBeanClassName() {
        if (beanClass==null){
            throw new NullPointerException("beanClass is null");
        }
        return beanClass.getSimpleName();
    }
}
