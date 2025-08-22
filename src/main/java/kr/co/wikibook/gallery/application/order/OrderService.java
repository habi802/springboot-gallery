package kr.co.wikibook.gallery.application.order;

import kr.co.wikibook.gallery.application.cart.CartMapper;
import kr.co.wikibook.gallery.application.item.ItemMapper;
import kr.co.wikibook.gallery.application.item.model.ItemGetRes;
import kr.co.wikibook.gallery.application.order.model.*;
import kr.co.wikibook.gallery.openfeign.order.KakaoPayReadyFeignClient;
import kr.co.wikibook.gallery.openfeign.order.model.KakaoPayReadyReq;
import kr.co.wikibook.gallery.openfeign.order.model.KakaoPayReadyRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderTempMapper orderTempMapper;
    private final OrderItemMapper orderItemMapper;
    private final ItemMapper itemMapper;
    private final CartMapper cartMapper;

    private final KakaoPayReadyFeignClient kakaoPayTokenFeignClient;

    @Transactional
    public int saveOrder(OrderPostReq req, Integer logginedMemberId) {
        int amount = calculateAmount(req.getItemIds());

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

    @Transactional
    public KakaoPayReadyRes getKakaoPayToken(int memberId, OrderTempReq req) {
        String uuid = UUID.randomUUID().toString();

        int amount = calculateAmount(req.getItemIds());

        OrderTempDto dto = OrderTempDto.builder()
                .uuid(uuid)
                .memberId(memberId)
                .guestId(req.getGuestId())
                .name(req.getName())
                .address(req.getAddress())
                .payment(req.getPayment())
                .cardNumber(req.getCardNumber())
                .amount(amount)
                .build();

        orderTempMapper.save(dto);

        KakaoPayReadyReq tokenReq = KakaoPayReadyReq.builder()
                .cid("TC0ONETIME")
                .partnerOrderId(uuid)
                .partnerUserId(String.valueOf(memberId))
                .itemName("Gallery")
                .quantity("1")
                .totalAmount(String.valueOf(amount))
                .taxFreeAmount("0")
                .approvalUrl("http://localhost:5173")
                .failUrl("http://localhost:5173")
                .cancelUrl("http://localhost:5173")
                .build();

        KakaoPayReadyRes result = kakaoPayTokenFeignClient.getToken(tokenReq);
        log.info("kakao pay Ready result: {}", result);
        return result;
    }

    private int calculateAmount(List<Integer> itemIds) {
        // 상품 정보 DB로부터 가져온다.
        List<ItemGetRes> itemList = itemMapper.findAllByIdIn(itemIds);
        //log.info("itemList={}", itemList);

        // 총 구매 가격을 콘솔에 출력!
        int amount = 0;
        for (ItemGetRes item : itemList) {
            int price = item.getPrice();
            int discountPer = item.getDiscountPer();
            amount += price - price * discountPer / 100;
        }
        //log.info("amount={}", amount);

        return amount;
    }
}
