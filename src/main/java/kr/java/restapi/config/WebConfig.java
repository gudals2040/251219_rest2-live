package kr.java.restapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// #(2)-4
@Configuration
@RequiredArgsConstructor // #(2)-8-1
public class WebConfig implements WebMvcConfigurer {

    // import org.springframework.beans.factory.annotation.Value;
//    @Value("${cors.allowed-origins}")
//    private String[] allowedOrigins; // ','로 쪼개서 문자열 배열로 받음

    // #(2)-8-2
    private final CorsProperties corsProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                // #(2)-8-3
//                .allowedOrigins(new String[]{"*"})
//                .allowedOrigins(allowedOrigins)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins(corsProperties.getAllowedOrigins().toArray(new String[0]))
                .allowedOrigins(corsProperties.getAllowedMethods().toArray(new String[0]))
                .allowedHeaders("*")
                .allowCredentials(true)
//                .maxAge(3600);
                .maxAge(corsProperties.getMaxAge());
    }
}
