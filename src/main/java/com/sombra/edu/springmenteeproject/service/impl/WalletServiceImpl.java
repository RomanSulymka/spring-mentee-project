package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.UserAccount;
import com.sombra.edu.springmenteeproject.entity.Wallet;
import com.sombra.edu.springmenteeproject.exception.NotFoundException;
import com.sombra.edu.springmenteeproject.exception.NullEntityReferenceException;
import com.sombra.edu.springmenteeproject.repository.WalletRepository;
import com.sombra.edu.springmenteeproject.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final WalletRepository repository;

    @Autowired
    public WalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Wallet> createNewWallet(Wallet wallet) {
        if (repository.findWalletById(wallet.getId()).isEmpty()) {
            return repository.saveWallet(wallet);
        } else {
            LOGGER.warn("Element is already exist");
            throw new NotFoundException("Element is already exist");
        }
    }

    @Override
    public Optional<Wallet> editWallet(Wallet wallet) {
        if (repository.findWalletById(wallet.getId()).isPresent()) {
            return repository.updateWallet(wallet);
        } else {
            LOGGER.warn("Can't find the element");
            throw new NotFoundException("Can't find the element");
        }
    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return repository.findWalletById(walletId).orElseThrow();
    }

    @Override
    public List<Wallet> getAllWallets() {
        return repository.getAllWallets();
    }

    @Override
    public void delete(Long walletId) {
        if (Objects.nonNull(findWalletById(walletId))) {
            repository.deleteWallet(walletId);
            LOGGER.warn("Element deleted");
        } else {
            LOGGER.warn("Element is not present");
            throw new NullEntityReferenceException();
        }
    }

    @Override
    public List<Wallet> searchWalletsByUserAccount(UserAccount userAccount) {
        return repository.searchWalletsByUserAccount(userAccount);
    }
}
