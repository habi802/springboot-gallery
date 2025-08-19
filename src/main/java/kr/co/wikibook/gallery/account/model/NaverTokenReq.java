package kr.co.wikibook.gallery.account.model;

import lombok.*;

import java.beans.ConstructorProperties;

@Getter
@ToString
public class NaverTokenReq {
    private String grantType;
    private String clientId;
    private String clientSecret;
    private String code;

    @ConstructorProperties({"grant_type", "client_id", "client_secret", "code"})
    public NaverTokenReq(String grantType, String clientId, String clientSecret, String code) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
    }
}
