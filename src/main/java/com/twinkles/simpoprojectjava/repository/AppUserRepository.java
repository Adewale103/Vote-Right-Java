package com.twinkles.simpoprojectjava.repository;

import com.twinkles.simpoprojectjava.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {
    boolean existsAppUserByBVN(String bvn);

    boolean existsAppUserByUsername(String username);
    AppUser findUserByBVN(String bvn);
}
