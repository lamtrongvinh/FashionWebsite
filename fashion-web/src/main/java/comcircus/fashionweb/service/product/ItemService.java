package comcircus.fashionweb.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.product.Item;
import comcircus.fashionweb.model.product.Product;

@Service
public interface ItemService {
    public Item saveItem(Product product, String size);
    public List<Item> getAllItem();
    public boolean checkItemExist(Product product, String size);
    public Long getItemID(Product product, String size);
    public void decreaseItemQuantity(Product product, String size, int quantity);
    public void cancelOrder(Product product, String size, int quantity);
}
