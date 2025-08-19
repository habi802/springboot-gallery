package kr.co.wikibook.gallery.account;

import kr.co.wikibook.gallery.account.model.KakaoTokenReq;
import kr.co.wikibook.gallery.account.model.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "kakao-token", url = "${constants.kakao-login.token-uri}")
public interface KakaoTokenFeignClient {
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponse getToken(@SpringQueryMap KakaoTokenReq req);
}
