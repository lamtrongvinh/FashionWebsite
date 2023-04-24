package comcircus.fashionweb.service.product;

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
    
}
