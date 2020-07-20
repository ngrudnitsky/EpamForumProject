package by.ngrudnitsky.service.impl;

import by.ngrudnitsky.data.PostRepository;
import by.ngrudnitsky.data.VoteRepository;
import by.ngrudnitsky.dto.VoteDto;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.Vote;
import by.ngrudnitsky.exeption.PostNotFoundException;
import by.ngrudnitsky.exeption.VoteServiceException;
import by.ngrudnitsky.service.AuthService;
import by.ngrudnitsky.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static by.ngrudnitsky.entity.VoteType.UP_VOTE;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;


    @Override
    public void vote(VoteDto voteDto) throws PostNotFoundException, VoteServiceException {
        Long postId = voteDto.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post Not Found by Id " + postId));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(
                post, authService.getCurrentUser()
        );
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new VoteServiceException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (UP_VOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
