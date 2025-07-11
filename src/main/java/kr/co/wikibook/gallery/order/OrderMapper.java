package kr.co.wikibook.gallery.order;

import kr.co.wikibook.gallery.order.model.OrderPostDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    int save(OrderPostDto dto);
}
