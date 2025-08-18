package kr.co.wikibook.gallery.account;

import kr.co.wikibook.gallery.account.model.KakaoTokenReq;
import kr.co.wikibook.gallery.account.model.KakaoTokenResponse;
import kr.co.wikibook.gallery.account.model.KakaoUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-login", url = "${constants.kakao-login.url}")
public interface KakaoFeignClient {
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponse getToken(@SpringQueryMap KakaoTokenReq req);

    @PostMapping("/v2/user/me")
    KakaoUserResponse getUser(@RequestHeader("Authorization") String accessToken);
}
