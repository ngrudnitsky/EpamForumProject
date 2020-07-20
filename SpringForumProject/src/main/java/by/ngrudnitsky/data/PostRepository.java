package by.ngrudnitsky.data;

import by.ngrudnitsky.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
//todo use update query

//    @Modifying
//    @Transactional
//    @Query("UPDATE Post SET title = :title, preview = :preview, content = :content, status = :status," +
//            " updated= :updated WHERE id = :id")
//    void update(@Param("title") String title, @Param("preview") String preview, @Param("content") String content,
//                @Param("status") Status status, @Param("updated") Date updated, @Param("id") Long id);
}
