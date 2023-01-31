package ducmin.demo.controller;

import ducmin.demo.dto.response.ResponMessage;
import ducmin.demo.model.Category;
import ducmin.demo.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryServiceImpl categoryService;

    @GetMapping
    public ResponseEntity<?> pageCategory(@PageableDefault(sort = "nameCategory", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Category> categoryPage = categoryService.findAll(pageable);
        if (categoryPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoryPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        if (categoryService.existsByNameCategory(category.getNameCategory())) {
            return new ResponseEntity<>(new ResponMessage("no_name_category"), HttpStatus.OK);
        }
        if (category.getAvatarCategory() == null) {
            return new ResponseEntity<>(new ResponMessage("no_avatar_category"), HttpStatus.OK);
        }
        categoryService.save(category);
        return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> pageSongByCateGory(@PathVariable Long id, @PageableDefault(sort = "nameCategory", direction = Sort.Direction.ASC) Pageable pageable) {
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/search/{nameCategory}")
    public ResponseEntity<?> searchCategory(@PathVariable String nameCategory, @PageableDefault(sort = "nameCategory", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Category> categoryPage = categoryService.findAllByNameCategoryContaining(nameCategory, pageable);
        if (categoryPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoryPage, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getListCategory() {
        List<Category> categoryList = categoryService.findAll();
        if (categoryList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id, @RequestBody Category category) {
        Optional<Category> category1 = categoryService.findById(id);
        if (!category1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (categoryService.existsByNameCategory(category.getNameCategory())) {
            if (!category.getAvatarCategory().equals(category1.get().getAvatarCategory())) {
//                    category1.get().setNameCategory(category.getNameCategory());
                category1.get().setAvatarCategory(category.getAvatarCategory());
                categoryService.save(category1.get());
                return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponMessage("no_name_category"), HttpStatus.OK);
        }
        category1.get().setNameCategory(category.getNameCategory());
        category1.get().setAvatarCategory(category.getAvatarCategory());
        categoryService.save(category1.get());
        return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
    }
}
