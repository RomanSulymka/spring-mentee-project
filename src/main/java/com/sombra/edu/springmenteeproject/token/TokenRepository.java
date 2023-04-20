package com.sombra.edu.springmenteeproject.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

  @Query("select t from tokens t inner join user_accounts u on t.userAccount.id = u.id where u.id = :id and (t.expired = false or t.revoked = false)")
  List<Token> findAllValidTokenByUser(@Param("id") Long id);

  Optional<Token> getByToken(String token);
}
