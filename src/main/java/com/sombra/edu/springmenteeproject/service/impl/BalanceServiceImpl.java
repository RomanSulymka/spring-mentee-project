package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.exception.NotFoundException;
import com.sombra.edu.springmenteeproject.repository.BalanceRepository;
import com.sombra.edu.springmenteeproject.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    @Override
    public BigDecimal getUserBalance(Long userId, Long walletId) {
        BigDecimal balance = balanceRepository.getBalanceByUserAccountIdAndWalletId(userId, walletId);
        if (balance == null) {
            throw new NotFoundException("Balance not found for user account ID " + userId + " and wallet ID " + walletId);
        }
        return balance;    }

    @Override
    public void increaseMoneyByCurrencyAndWalletId(BigDecimal quantity, Long walletId) {
        balanceRepository.increaseMoneyByCurrencyAndWalletId(walletId, quantity);
    }

    @Override
    public void subtractMoneyByCurrencyAndWalletId(BigDecimal quantity, Long walletId) {
        balanceRepository.subtractMoneyByCurrencyAndWalletId(walletId, quantity);
    }

    @Override
    public boolean isSameCurrencyInWallets(Long senderWalletsId, Long receiverWalletsId) {
        String senderCurrency = balanceRepository.getCurrencyByWalletId(senderWalletsId);
        String receiverCurrency = balanceRepository.getCurrencyByWalletId(receiverWalletsId);
        boolean sameCurrency = Objects.equals(senderCurrency, receiverCurrency);
        if (!sameCurrency) {
            log.error("The currencies in the wallets are not the same. Sender wallet currency: {}, receiver wallet currency: {}", senderCurrency, receiverCurrency);
        }
        return sameCurrency;
    }

}
