package kr.co.wikibook.gallery.application.order;

import kr.co.wikibook.gallery.application.order.model.OrderDetailGetReq;
import kr.co.wikibook.gallery.application.order.model.OrderDetailGetRes;
import kr.co.wikibook.gallery.application.order.model.OrderGetRes;
import kr.co.wikibook.gallery.application.order.model.OrderPostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int save(OrderPostDto dto);
    List<OrderGetRes> findAllByMemberIdOrderByIdDesc(int memberId);
    OrderDetailGetRes findByOrderIdAndMemberId(OrderDetailGetReq req);
}
