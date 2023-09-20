package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.dto.SendMoneyDTO;
import com.sombra.edu.springmenteeproject.entity.Balance;
import com.sombra.edu.springmenteeproject.entity.Money;
import com.sombra.edu.springmenteeproject.entity.Wallet;
import com.sombra.edu.springmenteeproject.repository.BalanceRepository;
import com.sombra.edu.springmenteeproject.repository.MoneyRepository;
import com.sombra.edu.springmenteeproject.repository.WalletRepository;
import com.sombra.edu.springmenteeproject.service.MoneyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MoneyServiceImpl implements MoneyService {

    private final MoneyRepository moneyRepository;
    private final BalanceRepository balanceRepository;
    private final WalletRepository walletRepository;

    @Override
    public List<Money> getAllMoney() {
        return moneyRepository.findAll();
    }

    @Override
    public List<SendMoneyDTO> getMoneyInformation() {
        return moneyRepository.findAll().stream()
                .flatMap(money -> balanceRepository.findBalancesByMoneyId(money.getId()).stream()
                        .map(balance -> {
                            Wallet walletName = walletRepository.findWalletByBalanceId(balance.getId());
                            return new SendMoneyDTO(
                                    balance.getId(),
                                    walletName.getWalletName(),
                                    money.getName(),
                                    money.getSymbol(),
                                    balance.getBalance()
                            );
                        })
                )
                .toList();
    }
}
