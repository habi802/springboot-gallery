package kr.co.wikibook.gallery.openfeign.account;

import kr.co.wikibook.gallery.openfeign.account.model.KakaoTokenReq;
import kr.co.wikibook.gallery.openfeign.account.model.KakaoTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "kakao-token", url = "${constants.kakao-login.token-uri}")
public interface KakaoTokenFeignClient {
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenRes getToken(@SpringQueryMap KakaoTokenReq req);
}
