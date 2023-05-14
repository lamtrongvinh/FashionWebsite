package comcircus.fashionweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long product_id;
    private String product_code;
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
}
