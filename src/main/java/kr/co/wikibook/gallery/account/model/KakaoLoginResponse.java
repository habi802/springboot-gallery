package kr.co.wikibook.gallery.account.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoLoginResponse {
    private String tokenType;
    private String accessToken;
    private String accessTokenExpriesIn;
    private String refreshToken;
    private String refreshTokenExpiresIn;
}
