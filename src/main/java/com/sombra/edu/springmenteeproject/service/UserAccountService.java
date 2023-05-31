package com.sombra.edu.springmenteeproject.service;

import com.sombra.edu.springmenteeproject.entity.UserAccount;

import java.util.List;

public interface UserAccountService {
    boolean checkIsUserAccountExist(Long senderAccountId, Long receiverAccountId);
    UserAccount getAccountById(Long senderAccountId);
    List<UserAccount> getAll();
    UserAccount getByEmail(String email);
}
