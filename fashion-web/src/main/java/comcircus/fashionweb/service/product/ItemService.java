package comcircus.fashionweb.service.product;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.product.Item;
import comcircus.fashionweb.model.product.Product;

@Service
public interface ItemService {
    public Item saveItem(Product product, String size);
}
