package kr.co.wikibook.gallery.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoPayReadyFeignConfig {
    @Value("${constants.kakao-pay.secret-key}")
    private String secretKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // RequestInterceptor는 Feign으로 API를 호출하기 직전에,
            // 헤더나 파라미터에 값을 강제로 집어넣는 역할
            template.header("Authorization", String.format("SECRET_KEY %s", secretKey));
            template.header("Content-Type", "application/json");
        };
    }
}
