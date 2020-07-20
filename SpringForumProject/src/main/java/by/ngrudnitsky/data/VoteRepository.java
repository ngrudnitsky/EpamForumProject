package by.ngrudnitsky.data;

import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);
}
