package kr.co.wikibook.gallery.application.account.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SocialLoginDto {
    private String socialId;
    private String loginId;
    private String loginType;
}
