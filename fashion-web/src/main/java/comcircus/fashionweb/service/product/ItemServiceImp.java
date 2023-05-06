package comcircus.fashionweb.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.product.Item;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.repository.ItemRepository;

@Service
public class ItemServiceImp implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item saveItem(Product product, String size) {
        Item item = new Item();
        item.setProduct_code(product.getProduct_code());
        item.setProduct_name(product.getProduct_name());
        item.setQuantity(product.getProduct_quantity());
        item.setSize(size);

        return itemRepository.save(item);
    }

    @Override
    public boolean checkItemExist(Product product, String size) {
        if (getItemID(product, size) != -1) {
            return true;
        }
        return false;
    }

    @Override
    public List<Item> getAllItem() {
        return (List<Item>) this.itemRepository.findAll();
    }

    @Override
    public void decreaseItemQuantity(Product product, String size, int quantity) {
        try {
            Long id = this.getItemID(product, size);
            Item item = this.itemRepository.findById(id).get();
            item.setQuantity(item.getQuantity() - quantity);
        } catch (Exception e) {
            System.out.println("item id không tồn tại");
        }
    }

    @Override
    public Long getItemID(Product product, String size) {
        List<Item> listItems = this.getAllItem();
        for (int i = 0; i < listItems.size(); i++) {
            Item cur_item = listItems.get(i);
            if (cur_item.getProduct_code().equals(product.getProduct_code()) 
                && cur_item.getQuantity() > 0 
                && cur_item.getSize().equals(size)) {
                    return cur_item.getId();
                } 
        }
        return Long.valueOf(-1);
    }
    
}
