package kr.co.wikibook.gallery.order;

import kr.co.wikibook.gallery.cart.CartMapper;
import kr.co.wikibook.gallery.item.ItemMapper;
import kr.co.wikibook.gallery.item.model.ItemGetRes;
import kr.co.wikibook.gallery.order.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ItemMapper itemMapper;
    private final CartMapper cartMapper;

    @Transactional
    public int saveOrder(OrderPostReq req, Integer logginedMemberId) {
        // 상품 정보 DB로부터 가져온다.
        List<ItemGetRes> itemList = itemMapper.findAllByIdIn(req.getItemIds());
        //log.info("itemList={}", itemList);

        // 총 구매 가격을 콘솔에 출력!
        int amount = 0;
        for (ItemGetRes item : itemList) {
            int price = item.getPrice();
            int discountPer = item.getDiscountPer();
            amount += price - price * discountPer / 100;
        }
        //log.info("amount={}", amount);

        OrderPostDto orderDto = OrderPostDto.builder()
                .memberId(logginedMemberId)
                .guestId(req.getGuestId())
                .name(req.getName())
                .address(req.getAddress())
                .payment(req.getPayment())
                .cardNumber(req.getCardNumber())
                .amount(amount)
                .build();

        //log.info("beforeOrderDto:{}", orderDto);
        orderMapper.save(orderDto);
        //log.info("afterOrderDto:{}", orderDto);

        OrderItemPostDto orderItemDto = new OrderItemPostDto(orderDto.getOrderId(), req.getItemIds());
        orderItemMapper.save(orderItemDto);

        if (logginedMemberId != null) {
            cartMapper.deleteByMemberId(logginedMemberId);
        }

        return 1;
    }

    public List<OrderGetRes> findAll(int memberId) {
        return orderMapper.findAllByMemberIdOrderByIdDesc(memberId);
    }

    public OrderDetailGetRes detail(OrderDetailGetReq req) {
        OrderDetailGetRes result = orderMapper.findByOrderIdAndMemberId(req);
        //log.info("result:{}", result);
        return result;
    }

    public KakaoPayTokenRes getKakaoPayToken(int memberId, OrderTempReq req) {
        OrderTempDto dto = OrderTempDto.builder()
                .memberId(memberId)
                .guestId(req.getGuestId())
                .address(req.getAddress())
                .payment(req.getPayment())
                .cardNumber(req.getCardNumber())
                .build();

        return null;
    }
}
