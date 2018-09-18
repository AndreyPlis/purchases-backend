package com.purchases.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.ApiKeyVehicle
import springfox.documentation.swagger.web.SecurityConfiguration
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalDate

@Configuration
@EnableSwagger2
class SwaggerConfig {

    internal fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("API Reference")
                .version("1.0.0")
                .build()
    }

    @Bean
    fun customImplementation(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securitySchemes(mutableListOf(apiKey()))
                .select().paths(PathSelectors.any())
                //.apis(RequestHandlerSelectors.any())  // If you want to list all the apis including springboots own
                .apis(RequestHandlerSelectors.basePackage("com.app.api"))
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .directModelSubstitute(LocalDate::class.java, String::class.java)
                .genericModelSubstitutes(ResponseEntity::class.java)
    }

    private fun apiKey(): ApiKey {
        //return new ApiKey("Authorization", "api_key", "header");
        return ApiKey("Authorization", "", "header")             // <<< === Create a Heaader (We are createing header named "Authorization" here)
    }

    @Bean
    internal fun security(): SecurityConfiguration {
        //return new SecurityConfiguration("emailSecurity_client", "secret", "Spring", "emailSecurity", "apiKey", ApiKeyVehicle.HEADER, "api_key", ",");
        return SecurityConfiguration("emailSecurity_client", "secret", "Spring", "emailSecurity", "", ApiKeyVehicle.HEADER, "", ",")
    }

    // This path will be called when swagger is loaded first time to get a token
    /*
    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration("session");
    }
    */

}
