package kr.java.restapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// (2)-4
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // import org.springframework.beans.factory.annotation.Value;
    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins; // ','로 쪼개서 문자열 배열로 받음

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
//                .allowedOrigins(new String[]{"*"})
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
