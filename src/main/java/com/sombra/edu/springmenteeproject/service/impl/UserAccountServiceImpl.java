package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.UserAccount;
import com.sombra.edu.springmenteeproject.exception.NotFoundException;
import com.sombra.edu.springmenteeproject.repository.UserAccountRepository;
import com.sombra.edu.springmenteeproject.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountServiceImpl implements UserAccountService, UserDetailsService {

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

    @Override
    public List<UserAccount> getAll() {
        return userAccountRepository.findAll();
    }

    @Override
    public UserAccount getByEmail(String email) {
        return userAccountRepository.getUserAccountByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount account = getByEmail(email);
        if (Objects.isNull(account)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", email));
        }
        return new User(account.getEmail(), account.getPassword(), true, true, true, true, new HashSet<>());
    }
}
