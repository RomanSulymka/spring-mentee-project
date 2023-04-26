package com.sombra.edu.springmenteeproject.service;

import java.math.BigDecimal;

public interface BalanceService {
    BigDecimal getUserBalance(Long userId, Long walletId);
    void increaseMoneyByCurrencyAndWalletId(BigDecimal quantity, Long walletId);
    void subtractMoneyByCurrencyAndWalletId(BigDecimal quantity, Long walletId);
    boolean isSameCurrencyInWallets(Long senderWalletsId, Long receiverWalletsId);

}
