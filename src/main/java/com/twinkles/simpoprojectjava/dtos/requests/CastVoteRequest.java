package com.twinkles.simpoprojectjava.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CastVoteRequest {
    private String party;
    private String VoteCategory;
    private String BVN;
    private String password;
}
