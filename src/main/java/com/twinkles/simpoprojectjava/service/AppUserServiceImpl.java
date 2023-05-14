package com.twinkles.simpoprojectjava.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flutterwave.rave.java.entry.bvnValidation;
import com.flutterwave.rave.java.payload.bvnload;
import com.twinkles.simpoprojectjava.dtos.requests.CastVoteRequest;
import com.twinkles.simpoprojectjava.dtos.requests.CreateAccountRequest;
import com.twinkles.simpoprojectjava.dtos.requests.ViewResultResponse;
import com.twinkles.simpoprojectjava.dtos.responses.CastVoteResponse;
import com.twinkles.simpoprojectjava.dtos.responses.CreateAccountResponse;
import com.twinkles.simpoprojectjava.dtos.responses.ValidateBVNResponse;
import com.twinkles.simpoprojectjava.exceptions.SimpoProjectException;
import com.twinkles.simpoprojectjava.model.*;
import com.twinkles.simpoprojectjava.repository.AppUserRepository;
import com.twinkles.simpoprojectjava.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService{

    private final KafkaTemplate<String, CastVoteRequest> kafkaTemplate;
    private final AppUserRepository appUserRepository;
    private final CandidateRepository candidateRepository;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) throws JsonProcessingException {
        if (appUserRepository.existsAppUserByBVN(createAccountRequest.getBVN())){
            throw new SimpoProjectException("User with provided BVN has already registered", 400);
        }
        ValidateBVNResponse validateBVNResponse = validateBVN(createAccountRequest.getBVN());
        if(!validateBVNResponse.getStatus().equals("success")){
            throw new SimpoProjectException(validateBVNResponse.getMessage(), 400);
        }
        AppUser appUser = buildAppUser(createAccountRequest, validateBVNResponse);
        appUserRepository.save(appUser);
        return new CreateAccountResponse("User profile successfully created");
    }

    @Override
    public CastVoteResponse castVoteForPresidency(CastVoteRequest castVoteRequest) {
        AppUser appUser = validateUserCredentials(castVoteRequest);
        if(appUser.isHasVotedForPresident()){
            throw new SimpoProjectException("You have already cast your vote for your preferred candidate", 400);
        }
        if(!VoteCategory.valueOf(castVoteRequest.getVoteCategory().toUpperCase()).equals(VoteCategory.PRESIDENCY)){
            throw new SimpoProjectException("Invalid vote category", 400);
        }
        Candidate candidate = checkCandidateValidity(castVoteRequest);
        candidate.setVoteCount(candidate.getVoteCount()+ 1);
        appUser.setHasVotedForPresident(true);
        appUserRepository.save(appUser);
        candidateRepository.save(candidate);
        return new CastVoteResponse("You have successfully casted your vote for your preferred presidential candidate");
    }


    @Override
    public CastVoteResponse castVoteForGovernorship(CastVoteRequest castVoteRequest) {
        try {
            AppUser appUser = validateUserCredentials(castVoteRequest);
            if(appUser.isHasVotedForGovernor()){
                throw new SimpoProjectException("You have already cast your vote for your preferred candidate", 400);
            }
            if(!VoteCategory.valueOf(castVoteRequest.getVoteCategory().toUpperCase()).equals(VoteCategory.GOVERNORSHIP)){
                throw new SimpoProjectException("Invalid vote category", 400);
            }
            Candidate candidate = checkCandidateValidity(castVoteRequest);
            candidate.setVoteCount(candidate.getVoteCount()+ 1);
            appUser.setHasVotedForGovernor(true);
            appUserRepository.save(appUser);
            candidateRepository.save(candidate);
            return new CastVoteResponse("You have successfully casted your vote for your preferred governorship candidate");
        } catch (Exception ex) {
            // If an error occurs, publish the message to the Kafka topic
            String topicName = "vote-casting-topic";
            kafkaTemplate.send(topicName, castVoteRequest);
            throw new SimpoProjectException("An error occurred while processing your request, please try again later", 500);
        }
    }

    private AppUser validateUserCredentials(CastVoteRequest castVoteRequest) {
        AppUser appUser = appUserRepository.findUserByBVN(castVoteRequest.getBVN());
        if(appUser == null || !appUser.getPassword().equals(castVoteRequest.getPassword())){
            throw new SimpoProjectException("Incorrect BVN or Password", 400);
        }
        return appUser;
    }

    private static boolean partyIsValid(CastVoteRequest castVoteRequest) {
        return EnumSet.allOf(Party.class)
                .stream()
                .anyMatch(party -> party.getName().equals(castVoteRequest.getParty().toUpperCase()));
    }

    @Override
    public CastVoteResponse castVoteForHouseOfRepresentative(CastVoteRequest castVoteRequest) {
        AppUser appUser = validateUserCredentials(castVoteRequest);
        if(appUser.isHasVotedForHouseOfRepMember()){
            throw new SimpoProjectException("You have already cast your vote for your preferred candidate", 400);
        }
        if(!VoteCategory.valueOf(castVoteRequest.getVoteCategory().toUpperCase()).equals(VoteCategory.HOUSE_OF_REPRESENTATIVE)){
            throw new SimpoProjectException("Invalid vote category", 400);
        }
        Candidate candidate = checkCandidateValidity(castVoteRequest);
        candidate.setVoteCount(candidate.getVoteCount()+ 1);
        appUser.setHasVotedForHouseOfRepMember(true);
        appUserRepository.save(appUser);
        candidateRepository.save(candidate);
        return new CastVoteResponse("You have successfully casted your vote for your preferred house of rep candidate");
    }

    @Override
    public CastVoteResponse castVoteForSenate(CastVoteRequest castVoteRequest) {
        AppUser appUser = validateUserCredentials(castVoteRequest);
        if(appUser.isHasVotedForSenateMember()){
            throw new SimpoProjectException("You have already cast your vote for your preferred candidate", 400);
        }
        if(!VoteCategory.valueOf(castVoteRequest.getVoteCategory().toUpperCase()).equals(VoteCategory.SENATE)){
            throw new SimpoProjectException("Invalid vote category", 400);
        }
        Candidate candidate = checkCandidateValidity(castVoteRequest);
        candidate.setVoteCount(candidate.getVoteCount()+ 1);
        appUser.setHasVotedForSenateMember(true);
        appUserRepository.save(appUser);
        candidateRepository.save(candidate);
        return new CastVoteResponse("You have successfully casted your vote for your preferred senate candidate");
    }

    @Override
    public CastVoteResponse castVoteForHouseOfAssembly(CastVoteRequest castVoteRequest) {
        AppUser appUser = validateUserCredentials(castVoteRequest);
        if(appUser.isHasVotedForHouseOfAssemblyMember()){
            throw new SimpoProjectException("You have already cast your vote for your preferred candidate", 400);
        }
        if(!VoteCategory.valueOf(castVoteRequest.getVoteCategory().toUpperCase()).equals(VoteCategory.HOUSE_OF_ASSEMBLY)){
            throw new SimpoProjectException("Invalid vote category", 400);
        }
        Candidate candidate = checkCandidateValidity(castVoteRequest);
        candidate.setVoteCount(candidate.getVoteCount()+ 1);
        appUser.setHasVotedForHouseOfAssemblyMember(true);
        appUserRepository.save(appUser);
        candidateRepository.save(candidate);
        return new CastVoteResponse("You have successfully casted your vote for your preferred house of assembly candidate");
    }

    @Override
    public ViewResultResponse viewPresidentialResultInPercentage() {
        List<Candidate> presidentialCandidates = candidateRepository.findCandidateByVoteCategory(VoteCategory.PRESIDENCY);
        return getResultFor(presidentialCandidates);
    }

    @Override
    public ViewResultResponse viewGovernorshipResultInPercentage() {
        List<Candidate> governorshipCandidates = candidateRepository.findCandidateByVoteCategory(VoteCategory.GOVERNORSHIP);
        return getResultFor(governorshipCandidates);
    }

    private ViewResultResponse getResultFor(List<Candidate> candidates) {
        long total = candidates.stream().mapToLong(Candidate::getVoteCount).count();
        Map<String, String> result = new HashMap<>();
        for(Candidate candidate : candidates){
            result.put(candidate.getParty().toString(), String.valueOf(candidate.getVoteCount()/total *100));
        }
        return new ViewResultResponse(result);
    }

    @Override
    public ViewResultResponse viewHouseOfRepresentativeResultInPercentage() {
        List<Candidate> houseOfRepCandidates = candidateRepository.findCandidateByVoteCategory(VoteCategory.HOUSE_OF_REPRESENTATIVE);
        return getResultFor(houseOfRepCandidates);
    }

    @Override
    public ViewResultResponse viewSenateResultInPercentage() {
        List<Candidate> senateCandidates = candidateRepository.findCandidateByVoteCategory(VoteCategory.SENATE);
        return getResultFor(senateCandidates);
    }

    @Override
    public ViewResultResponse viewHouseOfAssemblyResultInPercentage() {
        List<Candidate> houseOfAssemblyCandidates = candidateRepository.findCandidateByVoteCategory(VoteCategory.HOUSE_OF_ASSEMBLY);
        return getResultFor(houseOfAssemblyCandidates);
    }

    private AppUser buildAppUser(CreateAccountRequest createAccountRequest, ValidateBVNResponse validateBVNResponse) {
     return AppUser.builder()
                .BVN(validateBVNResponse.getData().getBvn())
                .date_of_birth(validateBVNResponse.getData().getDate_of_birth())
                .email(validateBVNResponse.getData().getEmail())
                .first_name(validateBVNResponse.getData().getFirst_name())
                .last_name(validateBVNResponse.getData().getLast_name())
                .middle_name(validateBVNResponse.getData().getMiddle_name())
                .gender(Gender.valueOf(validateBVNResponse.getData().getGender()))
                .password(createAccountRequest.getPassword())
                .phoneNumber(validateBVNResponse.getData().getPhone_number())
                .nationality(validateBVNResponse.getData().getNationality())
                .address(validateBVNResponse.getData().getAddress())
                .username(generateUsername(validateBVNResponse.getData().getFirst_name(), validateBVNResponse.getData().getLast_name()))
                .build();
    }

    private String generateUsername(String firstName, String lastName) {
        String username = (firstName + UUID.randomUUID().toString().substring(0,4) + lastName.substring(0,4)).toLowerCase();
        if(appUserRepository.existsAppUserByUsername(username)){
            generateUsername(firstName,lastName);
        }
        return username;
    }

    private ValidateBVNResponse validateBVN(String bvn) throws JsonProcessingException {
        bvnValidation bvnvalidation = new bvnValidation();
        bvnload bvnload = new bvnload();
        bvnload.setBvn(bvn);
        String response = bvnvalidation.bvnvalidate(bvnload);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, ValidateBVNResponse.class);
    }
}
