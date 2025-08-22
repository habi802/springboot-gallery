package kr.co.wikibook.gallery.application.item;

import kr.co.wikibook.gallery.application.item.model.ItemGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    List<ItemGetRes> findAllByIdIn(List<Integer> ids);
}
