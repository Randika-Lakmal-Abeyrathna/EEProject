package com.randikalakmal.supplierservice.repository;


import com.randikalakmal.supplierservice.model.User;
import com.randikalakmal.supplierservice.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken,Integer> {

    Optional<UserToken> findByToken(String token);

    void deleteUserTokenByTokenAndUser(String token, User user);
}
