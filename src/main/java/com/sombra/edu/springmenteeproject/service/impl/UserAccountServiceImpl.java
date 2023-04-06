package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.UserAccount;
import com.sombra.edu.springmenteeproject.repository.UserAccountRepository;
import com.sombra.edu.springmenteeproject.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public boolean checkIsUserAccountExist(Long senderAccountId, Long receiverAccountId) {
        return userAccountRepository.existsById(senderAccountId) && userAccountRepository.existsById(receiverAccountId);
    }

    @Override
    public UserAccount findAccountById(Long userId) {
        return userAccountRepository.findById(userId).orElseThrow();
    }
}
