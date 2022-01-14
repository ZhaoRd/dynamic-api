package io.github.zhaord.dynamicapi.annotation;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author zhao rende
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@RestController
public @interface DynamicApi {
}
