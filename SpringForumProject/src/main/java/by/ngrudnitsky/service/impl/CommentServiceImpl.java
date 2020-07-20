package by.ngrudnitsky.service.impl;

import by.ngrudnitsky.data.CommentRepository;
import by.ngrudnitsky.data.PostRepository;
import by.ngrudnitsky.data.UserRepository;
import by.ngrudnitsky.dto.CommentDto;
import by.ngrudnitsky.entity.Comment;
import by.ngrudnitsky.entity.NotificationEmail;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exeption.PostNotFoundException;
import by.ngrudnitsky.exeption.UserNotFoundException;
import by.ngrudnitsky.mapper.CommentMapper;
import by.ngrudnitsky.service.AuthService;
import by.ngrudnitsky.service.CommentService;
import by.ngrudnitsky.service.MailContentBuilder;
import by.ngrudnitsky.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Override
    public CommentDto save(CommentDto commentDot) throws PostNotFoundException {
        Post post = postRepository.findById(commentDot.getPostId()).orElseThrow(PostNotFoundException::new);
        User currentUser = authService.getCurrentUser();
        Comment comment = commentMapper.map(commentDot, post, currentUser);
        Comment savedComment = commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post.");
        sendCommentNotification(message, post.getUser());

        return commentMapper.mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> getAllCommentsForPost(Long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return commentRepository.findByPost(post).stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllCommentsForUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return commentRepository.findAllByUser(user).stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post",
                user.getEmail(), message));
    }
}
