package kr.co.wikibook.gallery.application.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoFeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // RequestInterceptor는 Feign으로 API를 호출하기 직전에,
            // 헤더나 파라미터에 값을 강제로 집어넣는 역할인데,
            // 하다 보니 필요없어졌음..
            //template.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        };
    }
}
