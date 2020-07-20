package by.ngrudnitsky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long postId;
    private String title;
    private String preview;
    private String content;
    private String url;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
}