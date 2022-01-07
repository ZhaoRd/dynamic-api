package io.github.zhaord.dynamicapi.core;

import io.github.zhaord.dynamicapi.annotation.DynamicApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class DynamicApiControllerProcessorTest {

    @Test
    void postProcessAfterInitializationWhenNoDynamicApi() {
        DynamicApiManager dynamicApiManager = Mockito.mock(DynamicApiManager.class);
        DynamicApiControllerProcessor processor=new DynamicApiControllerProcessor(dynamicApiManager);


        DemoService demo = new DemoService();
        Object demoService = processor.postProcessAfterInitialization(demo, "demoService");
        assertThat(demoService).isEqualTo(demo);
        Mockito.verify(dynamicApiManager,times(0))
                .register(Mockito.any(DynamicApiControllerInfo.class));
    }

    @Test
    void postProcessAfterInitializationWhenDynamicApi() {
        DynamicApiManager dynamicApiManager = Mockito.mock(DynamicApiManager.class);
        DynamicApiControllerProcessor processor=new DynamicApiControllerProcessor(dynamicApiManager);


        DemoApiService demo = new DemoApiService();
        Object demoService = processor.postProcessAfterInitialization(demo, "demoService");
        assertThat(demoService).isEqualTo(demo);
        Mockito.verify(dynamicApiManager,times(1))
                .register(Mockito.any(DynamicApiControllerInfo.class));
    }

    public static class DemoService{}

    @DynamicApi
    public static class DemoApiService{}

}