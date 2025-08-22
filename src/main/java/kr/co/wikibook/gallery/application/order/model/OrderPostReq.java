package kr.co.wikibook.gallery.application.order.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class OrderPostReq {
    private String guestId;
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String kakaoTid;
    private List<Integer> itemIds;
    private String orderId;
    private String pgToken;
}
