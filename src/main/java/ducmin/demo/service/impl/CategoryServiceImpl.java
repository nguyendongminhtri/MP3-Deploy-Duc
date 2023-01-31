package ducmin.demo.service.impl;

import ducmin.demo.model.Category;
import ducmin.demo.model.User;
import ducmin.demo.repository.ICategoryRepository;
import ducmin.demo.security.userprincal.UserDetailService;
import ducmin.demo.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    ICategoryRepository categoryRepository;
    @Autowired
    UserDetailService userDetailService;
    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category save(Category category) {
        User user = userDetailService.getCurrentUser();
        category.setUser(user);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Boolean existsByNameCategory(String nameCategory) {
        return categoryRepository.existsByNameCategory(nameCategory);
    }

    @Override
    public Page<Category> findAllByNameCategoryContaining(String nameCategory, Pageable pageable) {
        return categoryRepository.findAllByNameCategoryContaining(nameCategory,pageable);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }


}
