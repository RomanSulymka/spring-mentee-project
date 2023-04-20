package com.sombra.edu.springmenteeproject.repository;

import com.sombra.edu.springmenteeproject.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount getUserAccountByEmail(String email);
}
