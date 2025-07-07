package kr.co.wikibook.gallery.item;

import kr.co.wikibook.gallery.item.model.ItemGetRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<?> readAll(@RequestParam(name = "id", required = false) List<Integer> ids) {
        log.info("params: {}", ids);
        List<ItemGetRes> items = itemService.findAll(ids);
        return ResponseEntity.ok(items);
    }
}
