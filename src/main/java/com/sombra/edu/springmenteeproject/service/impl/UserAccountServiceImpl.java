package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.UserAccount;
import com.sombra.edu.springmenteeproject.exception.NotFoundException;
import com.sombra.edu.springmenteeproject.repository.UserAccountRepository;
import com.sombra.edu.springmenteeproject.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public boolean checkIsUserAccountExist(Long senderAccountId, Long receiverAccountId) {
        return userAccountRepository.existsById(senderAccountId) && userAccountRepository.existsById(receiverAccountId);
    }

    @Override
    public UserAccount getAccountById(Long userId) {
        if (userAccountRepository.findById(userId).isPresent()) {
            return userAccountRepository.getReferenceById(userId);
        } else {
            throw new NotFoundException("User is not found with userId " + userId);
        }
    }
}
