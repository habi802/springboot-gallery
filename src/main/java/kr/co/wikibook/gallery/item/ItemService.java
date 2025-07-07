package kr.co.wikibook.gallery.item;

import kr.co.wikibook.gallery.item.model.ItemGetRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemMapper itemMapper;

    public List<ItemGetRes> findAll(List<Integer> ids){
        return itemMapper.findAllByIdIn(ids);
    }
}
