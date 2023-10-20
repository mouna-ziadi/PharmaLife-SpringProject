package tn.esprit.Entities;
import java.util.HashSet;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table( name = "Article")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idArticle")
    private Long idArticle;
    private String NameArticle;
    private String DescriptionArticle;
    private String ImageArticle;
    private LocalDate DateArticle;


    @ManyToOne
    User userArticle;

    @OneToMany(mappedBy="articleComment")
    private Set<Comment> CommentsArticle;


    public void addComment(Comment comment) {
        if (CommentsArticle == null) {
            CommentsArticle = new HashSet<>();
        }
        CommentsArticle.add(comment);
        comment.setArticleComment(this);
    }
    }
