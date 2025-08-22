package kr.co.wikibook.gallery.openfeign.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoUserRes {
    private Long id;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {
        private Profile profile;

        @Data
        public static class Profile {
            private String nickname;
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }

        private String email;
    }
}
