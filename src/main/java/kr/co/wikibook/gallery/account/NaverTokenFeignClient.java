package kr.co.wikibook.gallery.account;

import kr.co.wikibook.gallery.account.model.NaverTokenReq;
import kr.co.wikibook.gallery.account.model.NaverTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@FeignClient(name = "naver-token", url = "${constants.naver-login.token-uri}")
public interface NaverTokenFeignClient {
    @GetMapping
    NaverTokenRes getToken(@ModelAttribute NaverTokenReq req);
}
