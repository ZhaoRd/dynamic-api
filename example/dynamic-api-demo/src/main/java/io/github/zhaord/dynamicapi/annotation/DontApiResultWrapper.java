package io.github.zhaord.dynamicapi.annotation;

import java.lang.annotation.*;

/**
 * @author zhaord
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface DontApiResultWrapper {
}
