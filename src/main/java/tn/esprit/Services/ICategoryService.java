package tn.esprit.Services;

import tn.esprit.Entities.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> retrieveAllCategories();

    Category addCategory(Category c);

    Category updateCategory (Category c);

    Category retrieveCategory (Integer idCategory);

    void deleteCategory( Integer idACategory);
     Category setArchivedCategory(Category c);
     List<Category> retrieveAllCategoriesArchived();
     Category setCancelArchivedCategory(Category c);
    Category ArCategory(Category c);
    boolean existsByName(String name);
    List<Category> retrieveAllCategoriesNotArchived();
    Category getByName(String name);
}
