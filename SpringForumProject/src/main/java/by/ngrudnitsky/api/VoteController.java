package by.ngrudnitsky.api;

import by.ngrudnitsky.dto.VoteDto;
import by.ngrudnitsky.exeption.PostNotFoundException;
import by.ngrudnitsky.exeption.VoteServiceException;
import by.ngrudnitsky.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/votes")
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        try {
            voteService.vote(voteDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (VoteServiceException e) {
            //todo ref
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
