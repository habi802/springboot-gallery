package kr.co.wikibook.gallery.openfeign.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoPayApproveReq {
    private String cid;
    private String tid;
    @JsonProperty("partner_order_id")
    private String orderId;
    @JsonProperty("partner_user_id")
    private String userId;
    @JsonProperty("pg_token")
    private String pgToken;
    @JsonProperty("total_amount")
    private String amount;
}
