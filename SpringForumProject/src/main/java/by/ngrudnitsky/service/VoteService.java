package by.ngrudnitsky.service;

import by.ngrudnitsky.dto.VoteDto;
import by.ngrudnitsky.exeption.PostNotFoundException;
import by.ngrudnitsky.exeption.VoteServiceException;

public interface VoteService {
    void vote(VoteDto voteDto) throws PostNotFoundException, VoteServiceException;
}
