package kr.co.wikibook.gallery.application.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemMapper orderItemMapper;
}
