package kr.co.wikibook.gallery.application.order;

import kr.co.wikibook.gallery.application.cart.CartMapper;
import kr.co.wikibook.gallery.application.item.ItemMapper;
import kr.co.wikibook.gallery.application.item.model.ItemGetRes;
import kr.co.wikibook.gallery.application.order.model.*;
import kr.co.wikibook.gallery.config.constants.ConstKakaoPay;
import kr.co.wikibook.gallery.openfeign.order.KakaoPayFeignClient;
import kr.co.wikibook.gallery.openfeign.order.model.KakaoPayApproveReq;
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

    private final KakaoPayFeignClient kakaoPayTokenFeignClient;

    private final ConstKakaoPay constKakaoPay;

    @Transactional
    public int saveOrder(OrderPostReq req, Integer memberId) {
        int amount = calculateAmount(req.getItemIds());

        OrderPostDto orderDto = OrderPostDto.builder()
                .memberId(memberId)
                .guestId(req.getGuestId())
                .name(req.getName())
                .address(req.getAddress())
                .payment(req.getPayment())
                .cardNumber(req.getCardNumber())
                .kakaoTid(req.getKakaoTid())
                .amount(amount)
                .build();

        if ("kakao".equals(req.getPayment())) {
            KakaoPayApproveReq payApproveReq = KakaoPayApproveReq.builder()
                    .cid("TC0ONETIME")
                    .tid(req.getKakaoTid())
                    .orderId(req.getOrderId())
                    .userId(memberId != null ? String.valueOf(memberId) : req.getGuestId())
                    .pgToken(req.getPgToken())
                    .amount(String.valueOf(amount))
                    .build();

            kakaoPayTokenFeignClient.approve(
                String.format("SECRET_KEY %s", constKakaoPay.getSecretKey()),
                payApproveReq
            );

            OrderTempUpdateDto tempUpdateDto = OrderTempUpdateDto.builder()
                    .uuid(req.getOrderId())
                    .status("COMPLETED")
                    .build();

            orderTempMapper.updateStatusByUuid(tempUpdateDto);
        }

        //log.info("beforeOrderDto:{}", orderDto);
        orderMapper.save(orderDto);
        //log.info("afterOrderDto:{}", orderDto);

        OrderItemPostDto orderItemDto = new OrderItemPostDto(orderDto.getOrderId(), req.getItemIds());
        orderItemMapper.save(orderItemDto);

        if (memberId != null) {
            cartMapper.deleteByMemberId(memberId);
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
    public KakaoPayReadyRes getKakaoPayToken(Integer memberId, OrderTempReq req) {
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

        KakaoPayReadyReq payReadyReq = KakaoPayReadyReq.builder()
                .cid("TC0ONETIME")
                .orderId(uuid)
                .userId(memberId != null ? String.valueOf(memberId) : dto.getGuestId())
                .itemName("Gallery")
                .quantity("1")
                .amount(String.valueOf(amount))
                .taxFreeAmount("0")
                .approvalUrl(String.format("http://localhost:5173/pay-ready-result?result=APPROVE&order_id=%s", uuid))
                .failUrl("http://localhost:5173/pay-ready-result?result=FAIL")
                .cancelUrl("http://localhost:5173/pay-ready-result?result=CANCEL")
                .build();

        KakaoPayReadyRes result = kakaoPayTokenFeignClient.ready(
            String.format("SECRET_KEY %s", constKakaoPay.getSecretKey()),
            payReadyReq
        );
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
