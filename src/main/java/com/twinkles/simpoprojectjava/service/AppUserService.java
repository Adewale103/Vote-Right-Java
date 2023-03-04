package com.twinkles.simpoprojectjava.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twinkles.simpoprojectjava.dtos.requests.CastVoteRequest;
import com.twinkles.simpoprojectjava.dtos.requests.CreateAccountRequest;
import com.twinkles.simpoprojectjava.dtos.requests.ViewPresidentialResultResponse;
import com.twinkles.simpoprojectjava.dtos.responses.CastVoteResponse;
import com.twinkles.simpoprojectjava.dtos.responses.CreateAccountResponse;

public interface AppUserService {
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) throws JsonProcessingException;
    CastVoteResponse castVoteForPresidency(CastVoteRequest castVoteRequest);
    CastVoteResponse castVoteForGovernorship(CastVoteRequest castVoteRequest);
    CastVoteResponse castVoteForHouseOfRepresentative(CastVoteRequest castVoteRequest);
    CastVoteResponse castVoteForSenate(CastVoteRequest castVoteRequest);
    CastVoteResponse castVoteForHouseOfAssembly(CastVoteRequest castVoteRequest);
    ViewPresidentialResultResponse viewPresidentialResultInPercentage();
}
