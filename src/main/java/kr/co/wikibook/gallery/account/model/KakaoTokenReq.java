package kr.co.wikibook.gallery.account.model;

import feign.Param;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoTokenReq {
    @Param("grant_type")
    private String grantType;
    @Param("client_id")
    private String clientId;
    @Param("redirect_uri")
    private String refreshUri;
    private String code;
}
