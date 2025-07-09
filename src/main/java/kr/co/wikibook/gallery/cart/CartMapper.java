package kr.co.wikibook.gallery.cart;

import kr.co.wikibook.gallery.cart.model.CartPostReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
    int save(CartPostReq req);
}
