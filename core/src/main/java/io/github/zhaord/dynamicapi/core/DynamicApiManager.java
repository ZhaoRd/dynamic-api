package io.github.zhaord.dynamicapi.core;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DynamicApiManager{

    private final Map<String, DynamicApiControllerInfo> dynamicApiControllers;
    public DynamicApiManager(){
        this.dynamicApiControllers=new HashMap<>();
    }

    public void register(DynamicApiControllerInfo controllerInfo)
    {
        dynamicApiControllers.put(controllerInfo.getBeanName(), controllerInfo);
    }

    public DynamicApiControllerInfo findOrNull(String controllerName)
    {
        return dynamicApiControllers.getOrDefault(controllerName,null);
    }

    public List<DynamicApiControllerInfo> getAll()
    {
        return new ArrayList<>(this.dynamicApiControllers.values());
    }

}
