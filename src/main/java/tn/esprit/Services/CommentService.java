package tn.esprit.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.Comment;
import tn.esprit.Repositories.CommentRepository;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Slf4j
@Service

public class CommentService implements  ICommentService {
    private final CommentRepository commentRepository;

    @Override
    public Comment addComment(Comment c) {

        return commentRepository.save(c);

    }



    @Override
    public Comment editComment(Comment c)  throws RuntimeException {

        if (c.getIdComment() == null) {
            throw new IllegalArgumentException("Comment ID cannot be null");
        }
        try {
            return commentRepository.save(c);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update Comment", e);
        }
    }
    @Override
    public void deleteComment(Long idComment) {
        Optional<Comment> comment = commentRepository.findById(idComment);

        comment.ifPresent(c -> {
            commentRepository.delete(c);
            log.info("Comment with id " + idComment + " has been deleted");
        });

    }
    @Override
    public List<Comment> retrieveAll() {
        return commentRepository.findAll();
    }


}
