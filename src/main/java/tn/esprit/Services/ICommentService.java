package tn.esprit.Services;

import tn.esprit.Entities.Comment;
import java.util.List;

public interface ICommentService {
    Comment editComment(Comment c)  throws RuntimeException;

    Comment addComment(Comment c);

    void deleteComment(Long idComment);

    List<Comment> retrieveAll();
}
