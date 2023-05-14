package comcircus.fashionweb.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.category.Category;

@Service
public interface CategoryService {
    public Category getCategory(Long id);
    public Category saveCategory(Category customer);
    public void deleteCategory(Long id);
    public List<Category> getCategorys();
    public Category updateCategory(Long id, Category customer);
}
