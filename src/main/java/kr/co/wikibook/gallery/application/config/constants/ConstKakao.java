package kr.co.wikibook.gallery.application.config.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "constants.kakao-login")
@RequiredArgsConstructor
@ToString
public class ConstKakao {
    private final String appKey;
    private final String redirectUri;
}
