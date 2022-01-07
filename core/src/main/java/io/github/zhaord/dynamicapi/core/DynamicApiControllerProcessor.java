package io.github.zhaord.dynamicapi.core;

import io.github.zhaord.dynamicapi.annotation.DynamicApi;
import lombok.AllArgsConstructor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DynamicApiControllerProcessor
        implements BeanPostProcessor {

    private final DynamicApiManager dynamicApiManager;

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {

        boolean hasDynamicApiAnnotation = bean.getClass().getAnnotation(DynamicApi.class)!=null;

        if (!hasDynamicApiAnnotation){
            return bean;
        }
        this.dynamicApiManager.register(DynamicApiHelper.buildControllerInfo(bean,beanName));

        return bean;
    }
}
