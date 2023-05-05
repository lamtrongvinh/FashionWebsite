package comcircus.fashionweb.service.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.product.Size;
import comcircus.fashionweb.repository.SizeRepository;

@Service
public class SizeServiceImp implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<Size> getListSizeChar() {
        List<Size> list = new ArrayList<>();
        List<Size> realList = (List<Size>) sizeRepository.findAll();
        for (int i = 0 ; i < realList.size(); i++) {
            String character = realList.get(i).getName();
            if (character.equals("S") || character.equals("M") || character.equals("L") || character.equals("XL")) {
                list.add(realList.get(i));
            }
        }

        return list;
    }

    @Override
    public List<Size> getListSizeNumber() {
        List<Size> list = new ArrayList<>();
        List<Size> realList = (List<Size>) sizeRepository.findAll();
        for (int i = 0 ; i < realList.size(); i++) {
            String character = realList.get(i).getName();
            if (character.equals("36") 
                || character.equals("37") 
                || character.equals("38") 
                || character.equals("39")
                || character.equals("39")
                || character.equals("40")
                || character.equals("41")
                || character.equals("42")) {
                list.add(realList.get(i));
            }
        }

        return list;
    }
    
}
