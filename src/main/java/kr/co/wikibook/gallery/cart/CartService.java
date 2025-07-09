package kr.co.wikibook.gallery.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapper cartMapper;
}
