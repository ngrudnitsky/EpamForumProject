package by.ngrudnitsky.data;

import by.ngrudnitsky.entity.Comment;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
