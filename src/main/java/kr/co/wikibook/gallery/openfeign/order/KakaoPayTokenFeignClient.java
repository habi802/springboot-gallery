package kr.co.wikibook.gallery.openfeign.order;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "kakao-pay-token", url = "${constants.kakao-pay.token-uri}")
public interface KakaoPayTokenFeignClient {
}
