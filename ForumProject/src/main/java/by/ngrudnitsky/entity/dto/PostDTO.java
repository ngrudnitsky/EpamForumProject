package by.ngrudnitsky.entity.dto;

import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.User;

import java.sql.Date;

public class PostDTO {
    private Integer id;
    private String title;
    private String preview;
    private String content;
    private Integer userId;
    private String status;
    private Date created;
    private User user;

    public static PostDTO convertToPostDTO(Post post) {
        PostDTO mainPagePostDTO = new PostDTO();
        mainPagePostDTO.setId(post.getId());
        mainPagePostDTO.setTitle(post.getTitle());
        mainPagePostDTO.setPreview(post.getPreview());
        mainPagePostDTO.setContent(post.getContent());
        mainPagePostDTO.setUserId(post.getUserId());
        mainPagePostDTO.setStatus(post.getStatus().name());
        mainPagePostDTO.setCreated(new Date(post.getCreated().getTime()));
        return mainPagePostDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}
