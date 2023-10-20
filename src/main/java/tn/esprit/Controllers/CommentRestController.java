package tn.esprit.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Comment;
import tn.esprit.Services.ICommentService;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/Comment")
public class CommentRestController {

    private  final ICommentService commentService;

    // http://localhost:8082/all-comments
    @GetMapping("/allcomments")
    public List<Comment> getComments() {
        return commentService.retrieveAll();
    }

    // http://localhost:8082/add-comment
    @PostMapping("/add-comment")
    public Comment addComment(@RequestBody Comment c) {
        return commentService.addComment(c);
    }

    // http://localhost:8082/edit-comment
    @PutMapping("/edit-comment")
    public Comment editComment(@RequestBody Comment c) {
        return commentService.editComment(c);
    }


    @PutMapping("/edit-comment/{id}")
    public Comment editComment(@PathVariable("id") Long commentId, @RequestBody Comment c) {
        // use CommentId to retrieve and update the Comment
        return commentService.editComment(c);
    }
    // http://localhost:8082/delet-comment/id
    @DeleteMapping("/delete-comment/{idComment}")
    public void deleteComment(@PathVariable("idComment") Long id) {
        commentService.deleteComment(id);
    }
}

