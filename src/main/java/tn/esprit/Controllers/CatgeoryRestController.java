package tn.esprit.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Category;
import tn.esprit.Services.CategoryService;
import tn.esprit.Services.ICategoryService;
import tn.esprit.Services.IProductService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/Category")
public class CatgeoryRestController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/all-categories")
    public List<Category> getAllCategories(){
        return categoryService.retrieveAllCategories();
    }
    @GetMapping("/all-categoriesArchived")
    public List<Category> retrieveAllCategoriesArchived(){
        return categoryService.retrieveAllCategoriesArchived();
    }
    @GetMapping("/all-categoriesNotArchived")
    public List<Category> retrieveAllCategoriesNotArchived(){
        return categoryService.retrieveAllCategoriesNotArchived();
    }

    @PostMapping("/add-category")
    public Category addCategory(@RequestBody Category c){
        return categoryService.addCategory(c);
    }
    @GetMapping("/exists/{name}")
    public Boolean categoryExists(@PathVariable("name") String name) {
        return categoryService.existsByName(name);

    }

    @GetMapping("/retrieve-category/{idCategory}")
    public Category retrieveCategory(@PathVariable("idCategory") Integer idCategory){
        return categoryService.retrieveCategory(idCategory);
    }

    @PutMapping("/update-category")
    public Category updateCategory(@RequestBody Category c){
        return categoryService.updateCategory(c);
    }
    @DeleteMapping("/delete-category/{idCategory}")
    public void deleteCategory(@PathVariable("idCategory") Integer idCategory){
        categoryService.deleteCategory(idCategory);
    }

    @PutMapping("/archive-category")
    public Category setArchivedCategory(@RequestBody Category c){
        return categoryService.setArchivedCategory(c);
    }
    @PutMapping("/cancel-archive-category")
    public Category setCancelArchivedCategory(@RequestBody Category c){
        return categoryService.setCancelArchivedCategory(c);
    }

    @PutMapping("/set-archive-category")
    public Category ArCategory(@RequestBody  Category c){
        return categoryService.ArCategory(c);
    }


    @GetMapping("/getByName/{name}")
    public Category getByName(@PathVariable("name") String name){
        return categoryService.getByName(name);
    }


}
