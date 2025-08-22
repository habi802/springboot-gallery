package kr.co.wikibook.gallery.order;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "kakao-pay-token", url = "${constants.kakao-pay.token-uri}")
public interface KakaoPayTokenFeignClient {
}
