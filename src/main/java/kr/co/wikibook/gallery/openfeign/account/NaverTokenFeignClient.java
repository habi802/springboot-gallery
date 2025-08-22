package kr.co.wikibook.gallery.openfeign.account;

import kr.co.wikibook.gallery.openfeign.account.model.NaverTokenReq;
import kr.co.wikibook.gallery.openfeign.account.model.NaverTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "naver-token", url = "${constants.naver-login.token-uri}")
public interface NaverTokenFeignClient {
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    NaverTokenRes getToken(@SpringQueryMap NaverTokenReq req);
}
