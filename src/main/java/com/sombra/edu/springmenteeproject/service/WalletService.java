package com.sombra.edu.springmenteeproject.service;

import com.sombra.edu.springmenteeproject.entity.Wallet;

import java.util.List;

public interface WalletService {
    Wallet createNewWallet(Wallet wallet);
    Wallet editWallet(Wallet wallet);
    Wallet getWalletById(Long walletId);
    List<Wallet> getAllWallets();
    void delete(Long walletId);
    boolean checkIsWalletsExist(Long senderWalletId, Long receiverWalletId);

}
