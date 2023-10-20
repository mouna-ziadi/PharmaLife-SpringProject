package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.Entities.Comment;




public interface CommentRepository  extends JpaRepository<Comment, Long> {

}
