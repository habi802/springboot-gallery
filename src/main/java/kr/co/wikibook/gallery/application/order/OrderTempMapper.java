package kr.co.wikibook.gallery.application.order;

import kr.co.wikibook.gallery.application.order.model.OrderTempDto;
import kr.co.wikibook.gallery.application.order.model.OrderTempUpdateDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderTempMapper {
    int save(OrderTempDto dto);
    int updateStatusByUuid(OrderTempUpdateDto dto);
}
