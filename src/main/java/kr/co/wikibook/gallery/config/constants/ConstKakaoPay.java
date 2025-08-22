package kr.co.wikibook.gallery.config.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "constants.kakao-pay")
@RequiredArgsConstructor
@ToString
public class ConstKakaoPay {
    private final String secretKey;
}
