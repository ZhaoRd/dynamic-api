package io.github.zhaord.dynamicapi.config;

import io.github.zhaord.dynamicapi.core.DynamicControllerHandlerMethodArgumentResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
public class MyWebMvcConfig implements WebMvcConfigurer {

    private final List<HttpMessageConverter<?>> converters;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        DynamicControllerHandlerMethodArgumentResolver resolver = new DynamicControllerHandlerMethodArgumentResolver(converters);
        resolvers.add(resolver);
    }

}
