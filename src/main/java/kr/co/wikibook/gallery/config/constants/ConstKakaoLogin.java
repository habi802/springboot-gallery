package kr.co.wikibook.gallery.config.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "constants.kakao-login")
@RequiredArgsConstructor
@ToString
public class ConstKakaoLogin {
    private final String appKey;
    private final String redirectUri;
}
