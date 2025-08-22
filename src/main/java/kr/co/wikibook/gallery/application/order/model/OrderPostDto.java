package kr.co.wikibook.gallery.application.order.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderPostDto {
    private int orderId;
    private Integer memberId;
    private String guestId;
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private long amount;
}
