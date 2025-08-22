package kr.co.wikibook.gallery.application.order;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.wikibook.gallery.application.account.etc.AccountConstants;
import kr.co.wikibook.gallery.config.util.HttpUtils;
import kr.co.wikibook.gallery.application.order.model.*;
import kr.co.wikibook.gallery.openfeign.order.model.KakaoPayReadyRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody OrderPostReq req, HttpServletRequest httpReq) {
        Integer logginedMemberId = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        //log.info("req: {}", req);
        int result = orderService.saveOrder(req, logginedMemberId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<?> findAll(HttpServletRequest httpReq) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        List<OrderGetRes> result = orderService.findAll(logginedMemberId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> findDetail(HttpServletRequest httpReq, @PathVariable int orderId) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        OrderDetailGetReq req = new OrderDetailGetReq();
        req.setOrderId(orderId);
        req.setMemberId(logginedMemberId);
        OrderDetailGetRes result = orderService.detail(req);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/kakao")
    public ResponseEntity<?> getKakaoPayToken(HttpServletRequest httpReq, @RequestBody OrderTempReq req) {
        int logginedMemberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        KakaoPayReadyRes result = orderService.getKakaoPayToken(logginedMemberId, req);
        return ResponseEntity.ok(result);
    }
}
