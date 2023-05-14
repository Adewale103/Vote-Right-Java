package com.twinkles.simpoprojectjava.utils;

import com.twinkles.simpoprojectjava.dtos.requests.CastVoteRequest;
import com.twinkles.simpoprojectjava.exceptions.SimpoProjectException;
import com.twinkles.simpoprojectjava.model.Candidate;
import com.twinkles.simpoprojectjava.model.Party;
import com.twinkles.simpoprojectjava.model.VoteCategory;
import com.twinkles.simpoprojectjava.repository.AppUserRepository;
import com.twinkles.simpoprojectjava.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UtilsClass {
    private final AppUserRepository appUserRepository;
    private final CandidateRepository candidateRepository;


    public  Candidate checkCandidateValidity(CastVoteRequest castVoteRequest) {
        if(!partyIsValid(castVoteRequest)) {
            throw new SimpoProjectException("Incorrect party name", 400);
        }
        Candidate candidate = candidateRepository.findCandidateByVoteCategoryAndParty(VoteCategory.valueOf(castVoteRequest.getVoteCategory()), Party.valueOf(castVoteRequest.getParty()));
        if(candidate == null){
            throw new SimpoProjectException("No candidate found!", 400);
        }
        return candidate;
    }
}
