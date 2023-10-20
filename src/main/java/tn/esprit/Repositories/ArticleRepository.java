package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.Entities.Article;

import java.util.List;

public interface ArticleRepository  extends JpaRepository<Article, Long> {
    Article findById(long articleId);


   // List<Article> findByNameArticle(String nameArticle);
}
