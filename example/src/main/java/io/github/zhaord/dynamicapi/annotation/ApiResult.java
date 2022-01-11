package io.github.zhaord.dynamicapi.annotation;

import lombok.Data;

/**
 * @author zhaord
 */
@Data
public class ApiResult<T> {
    private String code;
    private Boolean success;
    private String message;
    private boolean apiResult=true;
    private T data;

}
