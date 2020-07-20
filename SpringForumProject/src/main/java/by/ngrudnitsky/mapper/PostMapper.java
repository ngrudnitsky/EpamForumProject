package by.ngrudnitsky.mapper;

import by.ngrudnitsky.data.CommentRepository;
import by.ngrudnitsky.dto.PostDto;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;

    @Mapping(target = "created", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updated", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostDto postDto, User user);

    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostDto mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreated().toEpochMilli());
    }
}
