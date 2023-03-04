package com.twinkles.simpoprojectjava.model;

import lombok.Getter;

@Getter
public enum Party {
    PDP("PDP"), APC("APC"), LP("LP"), NNPP("NNPP"), ACN("ACN"), CPP("CPP"), CPC("CPC");
    private final String name;
    Party(String name){
        this.name = name;
    }
}
