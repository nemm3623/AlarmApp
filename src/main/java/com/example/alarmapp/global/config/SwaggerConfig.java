package com.example.alarmapp.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * Swagger (OpenAPI) 설정 클래스.
 * - Profile: "prod" 환경을 제외한 환경에서만 활성화됩니다.
 * - OpenAPI Bean: 전역적인 API 문서 정보를 설정합니다. (인증, 서버 URL 등)
 * - GroupedOpenApi Beans: API를 기능/도메인별로 그룹화합니다.
 */
@Profile("!prod")
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    /**
     * 전역 OpenAPI 설정을 담는 Bean.
     * API의 기본 정보, 서버 정보, 보안 관련 설정을 중앙에서 관리합니다.
     */
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Project API Document")
                .version("v1.0.0")
                .description("우리 프로젝트의 API 명세서입니다.");

        // JWT 인증 스키마 설정
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(SECURITY_SCHEME_NAME);

        // 서버 URL 설정 (개발, 로컬)
        Server localServer = new Server().url("http://localhost:8080").description("Local development server");
        Server devServer = new Server().url("https://dev.our-service.com").description("Development server");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme))
                .addSecurityItem(securityRequirement)
                .info(info)
                .servers(List.of(localServer, devServer));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("1. User API") // 그룹 이름 지정
                .pathsToMatch("/api/**") // 이 패턴에 해당하는 경로만 포함
                .build();
    }
}
