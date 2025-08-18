package kr.co.wikibook.gallery.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class KakaoUserResponse {
    private Long id;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {
        private Profile profile;
        private String email;

        @Data
        public static class Profile {
            private String nickname;
            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }
}
