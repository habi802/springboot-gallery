package kr.co.wikibook.gallery.order;

import kr.co.wikibook.gallery.item.ItemMapper;
import kr.co.wikibook.gallery.item.model.ItemGetRes;
import kr.co.wikibook.gallery.order.model.OrderItemPostDto;
import kr.co.wikibook.gallery.order.model.OrderPostDto;
import kr.co.wikibook.gallery.order.model.OrderPostReq;
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

    @Transactional
    public int saveOrder(OrderPostReq req, int logginedMemberId) {
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
                .name(req.getName())
                .address(req.getAddress())
                .payment(req.getPayment())
                .cardNumber(req.getCardNumber())
                .amount(amount)
                .build();

        //log.info("beforeOrderDto:{}", orderDto);
        int orderResult = orderMapper.save(orderDto);
        //log.info("afterOrderDto:{}", orderDto);

        OrderItemPostDto orderItemDto = new OrderItemPostDto(orderDto.getOrderId(), req.getItemIds());
        int orderItemResult = orderItemMapper.save(orderItemDto);

        return 1;
    }
}
