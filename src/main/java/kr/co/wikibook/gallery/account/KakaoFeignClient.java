package kr.co.wikibook.gallery.account;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "kakao-login", url = "${constants.kakao-login.url}")
public interface KakaoFeignClient {
}
