package jpaone.jpashop.service;


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

    public void saveItem(Item item){
        itemRepository.save(item);
    }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }


}
