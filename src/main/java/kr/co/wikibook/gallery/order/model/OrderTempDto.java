package kr.co.wikibook.gallery.order.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderTempDto {
    private String uuid;
    private Integer memberId;
    private String guestId;
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String kakaoTid;
    private long amount;
}
