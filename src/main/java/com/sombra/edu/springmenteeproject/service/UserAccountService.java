package com.sombra.edu.springmenteeproject.service;

import com.sombra.edu.springmenteeproject.entity.UserAccount;

public interface UserAccountService {
    boolean checkIsUserAccountExist(Long senderAccountId, Long receiverAccountId);
    UserAccount getAccountById(Long senderAccountId);
}
