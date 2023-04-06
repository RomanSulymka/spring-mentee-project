package com.sombra.edu.springmenteeproject.service;

public interface BalanceService {
    Double findBalanceByUserAccountIdAndWalletId(Long userId, Long walletId);
    void increaseMoneyByCurrencyAndWalletId(Double quantity, Long walletId);
    void subtractMoneyByCurrencyAndWalletId(Double quantity, Long walletId);
    boolean isSameCurrencyInWallets(Long senderWalletsId, Long receiverWalletsId);

}
