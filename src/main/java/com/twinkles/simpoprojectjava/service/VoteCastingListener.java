package com.twinkles.simpoprojectjava.service;

import com.twinkles.simpoprojectjava.dtos.requests.CastVoteRequest;
import com.twinkles.simpoprojectjava.exceptions.SimpoProjectException;
import com.twinkles.simpoprojectjava.model.AppUser;
import com.twinkles.simpoprojectjava.model.Candidate;
import com.twinkles.simpoprojectjava.model.VoteCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteCastingListener {

    private static final String VOTES_HASH_KEY = "votes";
    private final RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(topics = "vote-casting-topic", groupId = "group_id")
    public void consume(CastVoteRequest castVoteRequest) {
        try {
            // Attempt to save the data to Redis
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
            redisTemplate.opsForHash().put(VOTES_HASH_KEY, castVoteRequest.getBVN(), castVoteRequest);
        } catch (Exception ex) {
            // If an error occurs, log the error
            ex.printStackTrace();
        }
    }



}