package tn.esprit.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Article;
import tn.esprit.Entities.Comment;
import tn.esprit.Repositories.CommentRepository;
import tn.esprit.Repositories.ArticleRepository;
import tn.esprit.Services.ArticleService;
import tn.esprit.Services.IArticleService;

import java.util.HashMap;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/Article")

public class ArticleRestController {

    private final IArticleService articleService;
    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;


    // http://localhost:8082/all-articles
    @GetMapping("/all-articles")
    public List<Article> getArticles() {
        return articleService.retrieveAll();
    }

    // http://localhost:8082/add-article
    @PostMapping("/add-article")
    public Article addArticle(@RequestBody Article a) {
        return articleService.addArticle(a);
    }


    // http://localhost:8081/edit-product
    @PutMapping("/edit-article")
    public Article editArticle(@RequestBody Article a) {
        return articleService.editArticle(a);
    }


  /*  @PutMapping("/edit-article")
    public Article editArticle(@PathVariable("id") Long articleId, @RequestBody Article a) {
        // use articleId to retrieve and update the article
        return articleService.editArticle(a);
    } */
    // http://localhost:8082/delet-article/id
   /* @DeleteMapping("/delete-article/{idArticle}")
    public void deleteArticle(@PathVariable("idArticle") Long id) {
        articleService.deleteArticle(id);
    }
}*/

    @DeleteMapping("delete-article/{idArticle}")
    public void deleteArticle(@PathVariable("idArticle") Long idArticle) {
        articleService.deleteArticle(idArticle);
    }


    @GetMapping("/statisticsArticleByUser")
    HashMap<String, Integer> ArticlesByUsers(){
        return articleService.ArticlesByUsers();
    }

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<String> addCommentToArticle(@PathVariable Long articleId, @RequestBody Comment comment) {
        // Récupérer l'article par son ID
        Article article = articleRepository.findById(articleId).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        // Définir l'article du commentaire
        comment.setArticleComment(article);

        // Enregistrer le commentaire dans la base de données
        commentRepository.save(comment);

        // Ajouter le commentaire à l'article
        article.addComment(comment);
        articleRepository.save(article);

        return ResponseEntity.ok("Comment added to article successfully.");


    }




    @GetMapping("/article/{id}")
    public ResponseEntity<Article> findArticleById(@PathVariable("id") Long id) {
        Article article = articleService.findArticleById(id);

        if (article != null) {
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

   /* @GetMapping("/name/{nameArticle}")
    public List<Article> getArticlesByName(@PathVariable String nameArticle) {
        return IArticleService.retrieveArticlesByName(nameArticle);
    }*/
}