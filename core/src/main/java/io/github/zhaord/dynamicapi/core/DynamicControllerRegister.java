package io.github.zhaord.dynamicapi.core;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;

/**
 * @author zhao rende
 */
@Component
@RequiredArgsConstructor
public class DynamicControllerRegister implements InitializingBean {

    private final DynamicApiManager dynamicApiManager;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private RequestMappingInfo.BuilderConfiguration config =  new RequestMappingInfo.BuilderConfiguration();

    private void init(){
        List<DynamicApiControllerInfo> dynamicApiList = this.dynamicApiManager.getAll();
        for (DynamicApiControllerInfo controllerInfo:
                dynamicApiList) {
            this.processer(controllerInfo);
        }
    }

    private void processer(DynamicApiControllerInfo controllerInfo){

        for (Map.Entry<String,DynamicApiMethodInfo> entry:
             controllerInfo.getMethodMap().entrySet()) {
            RequestMappingInfo.Builder mappingInfoBuilder =
                    RequestMappingInfo.paths(DynamicApiHelper.url("api/service",
                                    DynamicApiHelper.toCamelCase(controllerInfo.getBeanClassName()),
                            entry.getKey()))
                            .methods(DynamicApiHelper.getRequestMethod(entry.getKey()))
                            .options(this.config);

            requestMappingHandlerMapping.registerMapping(
                    mappingInfoBuilder
                            .build(),
                    controllerInfo.getBeanName(),
                    entry.getValue().getMethod());

        }



    }

    @Override
    public void afterPropertiesSet() {

        this.config = new RequestMappingInfo.BuilderConfiguration();
        this.config.setTrailingSlashMatch(this.requestMappingHandlerMapping.useTrailingSlashMatch());
        this.config.setContentNegotiationManager(this.requestMappingHandlerMapping.getContentNegotiationManager());
        if (this.requestMappingHandlerMapping.getPatternParser() != null) {
            this.config.setPatternParser(this.requestMappingHandlerMapping.getPatternParser());
        }
        this.init();
    }
}
