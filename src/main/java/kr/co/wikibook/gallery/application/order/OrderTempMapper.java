package kr.co.wikibook.gallery.application.order;

import kr.co.wikibook.gallery.application.order.model.OrderTempDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderTempMapper {
    int save(OrderTempDto dto);
}
