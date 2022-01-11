package io.github.zhaord.dynamicapi.config;

import io.github.zhaord.dynamicapi.swaggeroperation.DynamicApiSwaggerOperationModelsProvider;
import io.github.zhaord.dynamicapi.swaggeroperation.DynamicApiSwaggerOperationParameterReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.WebMvcRequestHandler;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.OperationModelsProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author zhaord
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.github.zhaord.dynamicapi"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DynamicApi Example 接口文档")
                .description("测试DynamicApi接口文档")
                .version("1.0")
                .build();
    }
}
