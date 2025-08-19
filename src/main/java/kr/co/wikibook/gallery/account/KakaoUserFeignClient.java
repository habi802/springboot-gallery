package kr.co.wikibook.gallery.account;

import kr.co.wikibook.gallery.account.model.KakaoUserRes;
import kr.co.wikibook.gallery.account.model.NaverUserRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-user", url = "${constants.kakao-login.user-uri}")
public interface KakaoUserFeignClient {
    @PostMapping("/v2/user/me")
    KakaoUserRes getUser(@RequestHeader("Authorization") String accessToken);
}
