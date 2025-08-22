package kr.co.wikibook.gallery.application.order.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderTempUpdateDto {
    private String uuid;
    private String status;
}
