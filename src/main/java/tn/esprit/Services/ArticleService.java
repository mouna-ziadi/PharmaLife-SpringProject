package tn.esprit.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.Article;

import tn.esprit.Repositories.ArticleRepository;

import java.util.*;


@AllArgsConstructor
@Slf4j
@Service
public class ArticleService implements  IArticleService {
    private final ArticleRepository articleRepository;

    @Override
    public Article addArticle(Article a) {

        return articleRepository.save(a);

    }



    @Override
    public Article editArticle(Article a)  throws RuntimeException {

        if (a.getIdArticle() == null) {
            throw new IllegalArgumentException("Article ID cannot be null");
        }
        try {
            return articleRepository.save(a);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update article", e);
        }
    }
    public void deleteArticle(Long idArticle) {
        articleRepository.deleteById(idArticle);
    }


  /*  @Override
    public void deleteArticle(Long idArticle) {
        Optional<Article> article = articleRepository.findById(idArticle);

        article.ifPresent(a -> {
            articleRepository.delete(a);
            log.info("Article with id " + idArticle + " has been deleted");
        });
 }
*/



    @Override
    public List<Article> retrieveAll() {
        return articleRepository.findAll();
    }


    @Override
    public HashMap<String, Integer> ArticlesByUsers() {
        HashMap<String, Integer> map=new HashMap<>();
        List<Article> listDonations= (List<Article>) articleRepository.findAll();
        for (Article d:listDonations) {
            String user = d.getNameArticle();
            if(map.containsKey(user)){
                map.put(user,map.get(user)+1);
            }
            else {
                map.put(user,1);
            }
        }
        return map;
    }


    @Override
    public Article findArticleById(Long id) {
        // Implement the logic to retrieve the article by its ID from the repository
        return articleRepository.findById(id).orElse(null);
    }

   /* @Override
    public List<Article> retrieveArticlesByName(String nameArticle) {
        return articleRepository.findByNameArticle(nameArticle);
    }*/

}