package comcircus.fashionweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDetailsCart {
    //Details of product
    private Long product_id;
    private String product_name;
    private double product_price;
    private double product_discount;
    private int product_quantity;
    private boolean product_stock;
    private boolean product_live;
    private String product_desciption;
    private String product_image_name;
    private Long category_id;
    private String size;

    //Item quantity
    private Long cartItem_id;
    private int quantity;
    private double total_price;
}
