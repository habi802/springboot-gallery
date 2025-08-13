package kr.co.wikibook.gallery.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/account/kakao")
public class KakaoLoginController {
    @GetMapping("/callback")
    public String kakaoCallback(@RequestParam String code) {
        log.info("code: {}", code);
        return "카카오 로그인";
    }
}
