package kr.co.wikibook.gallery.openfeign.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoPayReadyRes {
    private String tid;
    @JsonProperty("next_redirect_pc_url")
    private String nextUrl;
    @JsonProperty("created_at")
    private String created;
}
