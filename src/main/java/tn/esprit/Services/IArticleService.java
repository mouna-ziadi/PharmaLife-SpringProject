package tn.esprit.Services;

import tn.esprit.Entities.Article;

import java.util.HashMap;
import java.util.List;

public interface IArticleService {
    Article addArticle(Article a);

    Article editArticle(Article a)  throws RuntimeException;

    void deleteArticle(Long idArticle);

    List<Article> retrieveAll();

    HashMap<String, Integer> ArticlesByUsers();

    Article findArticleById(Long id);



   // List<Article> retrieveArticlesByName(String nameArticle);
}
