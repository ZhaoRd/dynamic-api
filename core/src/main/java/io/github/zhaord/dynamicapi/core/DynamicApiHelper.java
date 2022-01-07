package io.github.zhaord.dynamicapi.core;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DynamicApiHelper {
    private DynamicApiHelper(){}

    public static RequestMethod getRequestMethod(String methodName){
        if (methodName.toUpperCase().startsWith("GET")||methodName.toUpperCase().startsWith("FIND")){
            return RequestMethod.GET;
        }
        if (methodName.toUpperCase().startsWith("DEL")
                ||methodName.toUpperCase().startsWith("DELETE")
                ||methodName.toUpperCase().startsWith("REMOVE")){
            return RequestMethod.DELETE;
        }
        if (methodName.toUpperCase().startsWith("ADD")
                ||methodName.toUpperCase().startsWith("INSERT")
                ||methodName.toUpperCase().startsWith("POST")){
            return RequestMethod.POST;
        }
        if (methodName.toUpperCase().startsWith("UPDATE")
                ||methodName.toUpperCase().startsWith("PUT")){
            return RequestMethod.PUT;
        }
        return defaultHttpMethod();
    }
    public static RequestMethod defaultHttpMethod()
    {
        return RequestMethod.POST;
    }

    public static String toCamelCase(String s){
        if(Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static List<Method> getMethodsOfType(Class<?> type)
    {
        Method[] methods = type.getDeclaredMethods();
        List<Method> publicMethodList = Arrays.stream(methods)
                .filter(c -> Modifier.isPublic(c.getModifiers()) && !Modifier.isStatic(c.getModifiers()) )
                .collect(Collectors.toList());
        return publicMethodList;
    }

    public static DynamicApiControllerInfo buildControllerInfo(final Object bean,String beanName){
        DynamicApiControllerInfo result = new DynamicApiControllerInfo(beanName,bean.getClass());
        List<Method> methodsOfType = getMethodsOfType(bean.getClass());
        List<DynamicApiMethodInfo> collect = methodsOfType.stream()
                .map(c -> buildMethodInfo(c)).collect(Collectors.toList());
        for (DynamicApiMethodInfo methodInfo:
             collect) {
            result.getMethodMap().put(methodInfo.getMethodName(),methodInfo);
        }
        return result;
    }

    public static DynamicApiMethodInfo buildMethodInfo(Method method){
        DynamicApiMethodInfo result = new DynamicApiMethodInfo(method.getName(),method);
        return result;
    }

    public static String url(String prefix,String beanName,String methodName){
        return "/"+ prefix+"/"+serviceName(beanName)+"/"+methodName;
    }

    public static String serviceName(String beanName){
        return beanName.replace("AppService","")
                .replace("Service","")
                .replace("ApplicationService","")
                .replace("Application","");
    }
}
