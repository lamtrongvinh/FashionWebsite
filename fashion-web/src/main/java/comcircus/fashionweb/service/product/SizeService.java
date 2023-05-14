package comcircus.fashionweb.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.product.Size;

@Service
public interface SizeService {
    public List<Size> getListSizeChar();
    public List<Size> getListSizeNumber();
    public List<Size> getListSizeNone();
}
