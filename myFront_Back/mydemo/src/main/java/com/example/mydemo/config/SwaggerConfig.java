package com.example.mydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: SwaggerConfig
 * @version: 1.0
 * @description: swagger-config类
 * @create: 2020-08-06 14:34
 *  *  通过@Configuration注解，让Spring来加载该类配置。
 *  *  通过@EnableSwagger2注解来启用Swagger2。
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.basePackage("com.example.mydemo"))
                // 要扫描的API(Controller)全局-(就是可以扫描所有的带有api注解的包并生成文档)
                 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    //配置在线文档的基本信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试系统")
                .description("用户模块")
                .termsOfServiceUrl("https://me.csdn.net/blog/miachen520")
                .version("1.0")
                .build();
    }


}
