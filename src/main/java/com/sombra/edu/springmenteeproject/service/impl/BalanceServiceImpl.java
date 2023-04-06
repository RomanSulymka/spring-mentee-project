package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.Balance;
import com.sombra.edu.springmenteeproject.repository.BalanceRepository;
import com.sombra.edu.springmenteeproject.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    @Autowired
    public BalanceServiceImpl(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public Double findBalanceByUserAccountIdAndWalletId(Long userId, Long walletId) {
        return balanceRepository.findBalanceByUserAccountIdAndWalletId(userId, walletId);
    }

    @Override
    public void increaseMoneyByCurrencyAndWalletId(Double quantity, Long walletId) {
        balanceRepository.increaseMoneyByCurrencyAndWalletId(walletId, quantity);
    }

    @Override
    public void subtractMoneyByCurrencyAndWalletId(Double quantity, Long walletId) {
        balanceRepository.subtractMoneyByCurrencyAndWalletId(walletId, quantity);
    }

    @Override
    public boolean isSameCurrencyInWallets(Long senderWalletsId, Long receiverWalletsId) {
        String senderCurrency = balanceRepository.findCurrencyByWalletId(senderWalletsId);
        String receiverCurrency = balanceRepository.findCurrencyByWalletId(receiverWalletsId);
        boolean sameCurrency = Objects.equals(senderCurrency, receiverCurrency);
        if (!sameCurrency) {
            log.error("The currencies in the wallets are not the same. Sender wallet currency: {}, receiver wallet currency: {}", senderCurrency, receiverCurrency);
        }
        return sameCurrency;
    }

}
