package kr.co.wikibook.gallery.openfeign.order;

import kr.co.wikibook.gallery.config.KakaoPayReadyFeignConfig;
import kr.co.wikibook.gallery.openfeign.order.model.KakaoPayReadyReq;
import kr.co.wikibook.gallery.openfeign.order.model.KakaoPayReadyRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "kakao-pay-token",
    url = "${constants.kakao-pay.ready-uri}",
    configuration = KakaoPayReadyFeignConfig.class
)
public interface KakaoPayReadyFeignClient {
    @PostMapping
    KakaoPayReadyRes getToken(@RequestBody KakaoPayReadyReq req);
}
