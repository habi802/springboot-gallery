package kr.co.wikibook.gallery.application.order;

import kr.co.wikibook.gallery.application.order.model.OrderItemPostDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {
    int save(OrderItemPostDto dto);
}
