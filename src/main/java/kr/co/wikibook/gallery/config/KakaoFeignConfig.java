package kr.co.wikibook.gallery.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoFeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // RequestInterceptor는 Feign으로 API를 호출하기 직전에,
            // 헤더나 파라미터에 값을 강제로 집어넣기 때문에,
            // 이 코드는 "/oauth/token" 를 호출할 땐 필요하나,
            // "/v2/user/me" 를 호출할 땐 불필요해서 주석 처리했음.
            //template.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        };
    }
}
