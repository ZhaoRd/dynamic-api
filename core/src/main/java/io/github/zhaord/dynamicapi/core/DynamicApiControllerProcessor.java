package io.github.zhaord.dynamicapi.core;

import io.github.zhaord.dynamicapi.annotation.DynamicApi;
import lombok.AllArgsConstructor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author zhao rende
 */
@Component
@AllArgsConstructor
public class DynamicApiControllerProcessor
        implements BeanPostProcessor {

    private final DynamicApiManager dynamicApiManager;

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {

        DynamicApi anno = AnnotationUtils.findAnnotation(bean.getClass(), DynamicApi.class);;
        if (anno==null){
            return bean;
        }
        this.dynamicApiManager.register(DynamicApiHelper.buildControllerInfo(bean,beanName));

        return bean;
    }

}
