package com.ai.website.common.config;

import com.ai.website.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2API文档的配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.ai.website")
                .title("门户网站前台系统")
                .description("前台系统相关接口文档")
                .contactName("lb")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
