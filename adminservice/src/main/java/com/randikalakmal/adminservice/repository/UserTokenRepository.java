package com.randikalakmal.adminservice.repository;


import com.randikalakmal.adminservice.model.User;
import com.randikalakmal.adminservice.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken,Integer> {

    Optional<UserToken> findByToken(String token);

    void deleteUserTokenByTokenAndUser(String token, User user);
}
