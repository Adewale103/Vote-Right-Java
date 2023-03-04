package com.twinkles.simpoprojectjava.repository;

import com.twinkles.simpoprojectjava.model.Candidate;
import com.twinkles.simpoprojectjava.model.Party;
import com.twinkles.simpoprojectjava.model.VoteCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CandidateRepository extends MongoRepository<Candidate, String> {
    Candidate findCandidateByVoteCategoryAndParty(VoteCategory voteCategory,Party party);
    List<Candidate> findCandidateByVoteCategory(VoteCategory voteCategory);
}
