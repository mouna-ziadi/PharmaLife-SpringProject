package tn.esprit.Services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.Category;
import tn.esprit.Repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;
    //back
    @Override
    public List<Category> retrieveAllCategories() {
        return categoryRepository.findAll();

    }
    @Override
    public List<Category> retrieveAllCategoriesArchived() {
        List<Category> categoryList=categoryRepository.findAll();
        List<Category> lisst=new ArrayList<>();
        for (Category c: categoryList) {
            if(!c.isArchived()){
                lisst.add(c);
            }

        }
        return lisst;
    }
    @Override
    public List<Category> retrieveAllCategoriesNotArchived() {
        List<Category> categoryList=categoryRepository.findAll();
        List<Category> lisst=new ArrayList<>();
        for (Category c: categoryList) {
            if(!c.isArchived()){
                lisst.add(c);
            }

        }
        return lisst;
    }

    @Override
    public Category addCategory(Category c) {

                c.setArchived(false);
                categoryRepository.save(c);



        return c;
    }
    @Override
    public boolean existsByName(String name) {
        Category c= categoryRepository.findByNameCategory(name);
        if(c==null){
            return false;
        }
        else
        return true;
    }
    @Override
    public Category getByName(String name) {
       return categoryRepository.findByNameCategory(name);

    }


    @Override
    public Category updateCategory(Category c) {
        categoryRepository.save(c);
        return c;
    }

    @Override
    public Category retrieveCategory(Integer idCategory) {
        return categoryRepository.findById(idCategory).get();
    }

    @Override
    public void deleteCategory(Integer idCategory) {
        categoryRepository.deleteById(idCategory);

    }

    //@Scheduled(cron = "* * */2 * * *")
    public void deleteCategoryScheduler() {
        List<Category> categoryList=categoryRepository.findAll();
        for (Category c:categoryList
             ) {
            if (!c.isArchived()) {
                categoryRepository.deleteById(c.getIdCategory());
            }
        }

    }

    @Override
    public Category setArchivedCategory(Category c){
        c.setArchived(false);
        categoryRepository.save(c);
        return c;
    }
    @Override
    public Category setCancelArchivedCategory(Category c){
        c.setArchived(true);
        categoryRepository.save(c);
        return c;
    }
    @Override
    public Category ArCategory(Category c){
        if(c.isArchived()){
            c.setArchived(false);
        }
        else c.setArchived(true);
        categoryRepository.save(c);
        return c;

    }

}
