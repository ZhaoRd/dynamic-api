package io.github.zhaord.dynamicapi.service;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaord
 */
@Data
@Builder
public class UserDto {
    private Long id;
    private String name;
}
