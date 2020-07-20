package by.ngrudnitsky.data;

import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Post SET title = :title, preview = :preview, content = :content, status = :status," +
            " updated= :updated WHERE id = :id")
    void update(@Param("title") String title, @Param("preview") String preview, @Param("content") String content,
                @Param("status") Status status, @Param("updated") Date updated, @Param("id") Long id);
}
