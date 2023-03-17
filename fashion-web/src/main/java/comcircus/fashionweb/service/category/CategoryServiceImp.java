package comcircus.fashionweb.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.category.Category;
import comcircus.fashionweb.repository.CategoryRepository;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category saveCategory(Category customer) {
        return categoryRepository.save(customer);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getCategorys() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category exitsCategory = categoryRepository.findById(id).get();
        exitsCategory.setName(category.getName());
        return categoryRepository.save(exitsCategory);
    }
    
}
