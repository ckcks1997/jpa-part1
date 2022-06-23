package jpaone.jpashop.service;


import jpaone.jpashop.domain.item.Book;
import jpaone.jpashop.domain.item.Item;
import jpaone.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService { //음.. 간단하면 별도 서비스없이 컨트롤러에서 바로 repository불러도 될듯

    private final ItemRepository itemRepository;

    public void saveItem(Item item){ //1. 준영속 merge 변경방법
        //머지 방법은 아예 값을 모두 교체함, 병합시 값이 없으면 null로 업데이트되므로 주의
        itemRepository.save(item);
    }

    @Transactional //2. 변경감지기능 사용방법 (리턴값 없어도 됨) 이거추천
    public Item updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        return findItem;
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }


}
