package kr.co.wikibook.gallery.openfeign.account;

import kr.co.wikibook.gallery.openfeign.account.model.NaverUserRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naver-user", url = "${constants.naver-login.user-uri}")
public interface NaverUserFeignClient {
    @GetMapping
    NaverUserRes getUser(@RequestHeader("Authorization") String accessToken);
}
