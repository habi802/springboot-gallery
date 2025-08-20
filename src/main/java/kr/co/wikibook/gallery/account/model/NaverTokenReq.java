package kr.co.wikibook.gallery.account.model;

import feign.Param;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NaverTokenReq {
    @Param("grant_type")
    private String grantType;
    @Param("client_id")
    private String clientId;
    @Param("client_secret")
    private String clientSecret;
    private String code;
}
