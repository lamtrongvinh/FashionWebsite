package comcircus.fashionweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    private Long customer_id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String address;
    private String url_image;
    private String email;
}
