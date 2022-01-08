package io.github.zhaord.dynamicapi.core;

import io.github.zhaord.dynamicapi.annotation.DynamicApi;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

/**
 * @author zhao rende
 * 针对 app service ，需要针对参数和返回值进行处理
 * {@link RestController}  中继承 @ResponseBody ,如果无此注解，则会返回404，因为返回值内容会使用ModelViewer解析
 * {@link RequestBody} 是针对http请求中body是json对象,context-type是application-json
 *
 */
public class DynamicControllerHandlerMethodArgumentResolver extends RequestResponseBodyMethodProcessor {
    public DynamicControllerHandlerMethodArgumentResolver(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {

        Class<?> declaringClass = returnType.getMember().getDeclaringClass();
        if (declaringClass.getAnnotation(DynamicApi.class)!=null){
            return true;
        }
        return super.supportsParameter(returnType);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getMethod().getName().toUpperCase().startsWith("GET")){
            return super.supportsParameter(parameter);
        }

        Class<?> declaringClass = parameter.getMember().getDeclaringClass();
        if (declaringClass.getAnnotation(DynamicApi.class)!=null){
            return true;
        }
        return super.supportsParameter(parameter);
    }

}
