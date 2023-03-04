package com.twinkles.simpoprojectjava.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.twinkles.simpoprojectjava.dtos.requests.CastVoteRequest;
import com.twinkles.simpoprojectjava.dtos.requests.CreateAccountRequest;
import com.twinkles.simpoprojectjava.dtos.requests.ViewResultResponse;
import com.twinkles.simpoprojectjava.dtos.responses.CastVoteResponse;
import com.twinkles.simpoprojectjava.dtos.responses.CreateAccountResponse;
import com.twinkles.simpoprojectjava.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vote-right/")
@RequiredArgsConstructor
public class AppUserController {
    private AppUserService appUserService;

    @PostMapping("register")
    public ResponseEntity<?> CreateAccount(@RequestBody CreateAccountRequest createAccountRequest) throws JsonProcessingException {
        CreateAccountResponse createAccountResponse = appUserService.createAccount(createAccountRequest);
        return ResponseEntity.status(HttpStatus.OK).body(createAccountResponse);
    }

    @PostMapping("presidency/vote")
    public ResponseEntity<?>  castVoteForPresidency(@RequestBody CastVoteRequest castVoteRequest)  {
        CastVoteResponse castVoteResponse = appUserService.castVoteForPresidency(castVoteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(castVoteResponse);
    }

    @PostMapping("governorship/vote")
    public ResponseEntity<?>  castVoteForGovernorship(@RequestBody CastVoteRequest castVoteRequest)  {
        CastVoteResponse castVoteResponse = appUserService.castVoteForGovernorship(castVoteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(castVoteResponse);
    }
    @PostMapping("house0fassembly/vote")
    public ResponseEntity<?>  castVoteForHouseOfAssembly(@RequestBody CastVoteRequest castVoteRequest)  {
        CastVoteResponse castVoteResponse = appUserService.castVoteForHouseOfAssembly(castVoteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(castVoteResponse);
    }
    @PostMapping("houseofrep/vote")
    public ResponseEntity<?>  castVoteForHouseOfRepresentative(@RequestBody CastVoteRequest castVoteRequest)  {
        CastVoteResponse castVoteResponse = appUserService.castVoteForHouseOfRepresentative(castVoteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(castVoteResponse);
    }
    @PostMapping("senate/vote")
    public ResponseEntity<?>  castVoteForSenate(@RequestBody CastVoteRequest castVoteRequest)  {
        CastVoteResponse castVoteResponse = appUserService.castVoteForSenate(castVoteRequest);
        return ResponseEntity.status(HttpStatus.OK).body(castVoteResponse);
    }

    @GetMapping("presidential/view")
    public ResponseEntity<?> viewPresidentialResultInPercentage ()  {
        ViewResultResponse viewPresidentialResultResponse = appUserService.viewPresidentialResultInPercentage();
        return ResponseEntity.status(HttpStatus.OK).body(viewPresidentialResultResponse);
    }

    @GetMapping("governorship/view")
    public ResponseEntity<?> viewGovernorshipResultInPercentage()  {
        ViewResultResponse viewGovernorshipResultResponse = appUserService.viewGovernorshipResultInPercentage();
        return ResponseEntity.status(HttpStatus.OK).body(viewGovernorshipResultResponse);
    }

    @GetMapping("houseofassembly/view")
    public ResponseEntity<?> viewHouseOfAssemblyResultInPercentage()  {
        ViewResultResponse viewHouseOfAssemblyResultResponse = appUserService.viewHouseOfAssemblyResultInPercentage();
        return ResponseEntity.status(HttpStatus.OK).body(viewHouseOfAssemblyResultResponse);
    }

    @GetMapping("houseofrepresentative/view")
    public ResponseEntity<?> viewHouseOfRepresentativeResultInPercentage()  {
        ViewResultResponse viewHouseOfRepresentativeResultResponse = appUserService.viewHouseOfRepresentativeResultInPercentage();
        return ResponseEntity.status(HttpStatus.OK).body(viewHouseOfRepresentativeResultResponse);
    }

    @GetMapping("senate/view")
    public ResponseEntity<?> viewSenateResultInPercentage()  {
        ViewResultResponse viewSenateResultInPercentageResultResponse = appUserService.viewSenateResultInPercentage();
        return ResponseEntity.status(HttpStatus.OK).body(viewSenateResultInPercentageResultResponse);
    }
}
