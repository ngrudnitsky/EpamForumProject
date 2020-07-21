package by.ngrudnitsky.service;

import by.ngrudnitsky.dto.VoteDto;
import by.ngrudnitsky.exception.PostNotFoundException;
import by.ngrudnitsky.exception.VoteServiceException;

public interface VoteService {
    void vote(VoteDto voteDto) throws PostNotFoundException, VoteServiceException;
}
