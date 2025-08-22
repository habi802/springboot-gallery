package kr.co.wikibook.gallery.openfeign.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NaverUserRes {
    @JsonProperty("resultcode")
    private String resultCode;
    private String message;
    private Response response;

    @Data
    public static class Response {
        private String id;
        private String nickname;
        @JsonProperty("profile_image")
        private String profileImage;
        private String email;
        private String name;
    }
}
