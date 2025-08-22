package kr.co.wikibook.gallery.application.order.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailGetReq {
    private int orderId;
    private int memberId;
}
