package io.github.zhaord.dynamicapi.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.zhaord.dynamicapi.annotation.ApiResult;
import io.github.zhaord.dynamicapi.annotation.DynamicApi;
import io.github.zhaord.dynamicapi.annotation.DontApiResultWrapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaord
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
// 拦截dynamicApi.class
@RestControllerAdvice(annotations = {
        DynamicApi.class
})
@AllArgsConstructor
public class ApiResultResponseAdvice  implements ResponseBodyAdvice<Object> {
    private final ObjectMapper json;
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 用 DontApiResultWrapper 注解的方法
        if (returnType.hasMethodAnnotation(DontApiResultWrapper.class)){
            return false;
        }

        // 用 DontApiResultWrapper 注解的类
        if (AnnotationUtils.findAnnotation(returnType.getDeclaringClass(), DontApiResultWrapper.class)!=null){
            return false;
        }

        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        servletResponse.addHeader("API_RESPONSE","true");
        if (body instanceof ApiResult){
            return body;
        }

        if(body instanceof String){
            ApiResult apiResult=new ApiResult();
            apiResult.setCode(String.valueOf(servletResponse.getStatus()));
            apiResult.setData(body);
            apiResult.setSuccess(true);
            apiResult.setApiResult(true);
            return json.writeValueAsString(apiResult);
        }

        ApiResult apiResult=new ApiResult();
        apiResult.setCode(String.valueOf(servletResponse.getStatus()));
        apiResult.setData(body);
        apiResult.setSuccess(true);
        apiResult.setApiResult(true);
        return apiResult;
    }
}
