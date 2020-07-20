package by.ngrudnitsky.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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