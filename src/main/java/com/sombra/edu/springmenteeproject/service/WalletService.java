package com.sombra.edu.springmenteeproject.service;

import com.sombra.edu.springmenteeproject.entity.UserAccount;
import com.sombra.edu.springmenteeproject.entity.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletService {
    Optional<Wallet> createNewWallet(Wallet wallet);
    Optional<Wallet> editWallet(Wallet wallet);
    Wallet findWalletById(Long walletId);
    List<Wallet> getAllWallets();
    void delete(Long walletId);
    List<Wallet> searchWalletsByUserAccount(UserAccount userAccount);
}
