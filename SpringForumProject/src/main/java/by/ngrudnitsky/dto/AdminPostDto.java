package by.ngrudnitsky.dto;

import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.EnumUtils;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminPostDto {
    private Long id;
    private String title;
    private String preview;
    private String content;
    private String status;

    public Post toPost(){
        Post post = new Post();
        post.setPostId(id);
        post.setTitle(title);
        post.setPreview(preview);
        post.setContent(content);
        post.setStatus(EnumUtils.getEnum(Status.class, status));
        return post;
    }

    public static AdminPostDto fromPost(Post post) {
        AdminPostDto postDto = new AdminPostDto();
        postDto.setId(post.getPostId());
        postDto.setTitle(post.getTitle());
        postDto.setPreview(post.getPreview());
        postDto.setContent(post.getContent());
        postDto.setStatus(post.getStatus().name());
        return postDto;
    }
}
