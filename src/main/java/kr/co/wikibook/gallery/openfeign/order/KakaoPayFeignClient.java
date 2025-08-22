package kr.co.wikibook.gallery.openfeign.order;

import kr.co.wikibook.gallery.openfeign.order.model.KakaoPayApproveReq;
import kr.co.wikibook.gallery.openfeign.order.model.KakaoPayReadyReq;
import kr.co.wikibook.gallery.openfeign.order.model.KakaoPayReadyRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-pay", url = "${constants.kakao-pay.request-uri}")
public interface KakaoPayFeignClient {
    @PostMapping(value = "/ready", consumes = MediaType.APPLICATION_JSON_VALUE)
    KakaoPayReadyRes ready(@RequestHeader("Authorization") String authorization,
                           @RequestBody KakaoPayReadyReq req);

    @PostMapping(value = "/approve", consumes = MediaType.APPLICATION_JSON_VALUE)
    void approve(@RequestHeader("Authorization") String authorization,
                 @RequestBody KakaoPayApproveReq req);
}
