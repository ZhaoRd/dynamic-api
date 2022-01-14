package io.github.zhaord.dynamicapi.service;

import io.github.zhaord.dynamicapi.annotation.DynamicApi;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author zhaord
 */
@DynamicApi
public class VerifyDemoService {

    @Validated
    public Integer add(@NotNull AddDto input){
        return input.getA()+input.getB();
    }

    @Data
    public static class AddDto{
        @NotNull
        private Integer a;
        @NotNull
        private Integer b;
    }
}
