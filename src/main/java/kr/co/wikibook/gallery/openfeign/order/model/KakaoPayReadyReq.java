package kr.co.wikibook.gallery.openfeign.order.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoPayReadyReq {
    private String cid;
    @JsonProperty("partner_order_id")
    private String partnerOrderId;
    @JsonProperty("partner_user_id")
    private String partnerUserId;
    @JsonProperty("item_name")
    private String itemName;
    private String quantity;
    @JsonProperty("total_amount")
    private String totalAmount;
    @JsonProperty("tax_free_amount")
    private String taxFreeAmount;
    @JsonProperty("approval_url")
    private String approvalUrl;
    @JsonProperty("fail_url")
    private String failUrl;
    @JsonProperty("cancel_url")
    private String cancelUrl;
}
