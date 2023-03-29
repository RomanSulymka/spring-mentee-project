package com.sombra.edu.springmenteeproject.repository;

import com.sombra.edu.springmenteeproject.entity.UserAccount;
import com.sombra.edu.springmenteeproject.entity.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sombra.edu.springmenteeproject.util.InitDataUtil.initializeWallets;

@Repository
public class WalletRepository implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletRepository.class);
    private static final List<Wallet> walletList = new ArrayList<>();

    public void initData() {
        initializeWallets(4, walletList);
    }

    public List<Wallet> getAllWallets() {
        return walletList;
    }

    public Optional<Wallet> findWalletById(Long id) {
        return walletList.stream()
                .filter(wallet -> wallet.getId().equals(id))
                .findFirst();
    }

    public List<Wallet> searchWalletsByUserAccount(UserAccount userAccount) {
        return walletList.stream()
                .filter(wallet -> wallet.getUserAccount().equals(userAccount))
                .collect(Collectors.toList());
    }

    public Optional<Wallet> saveWallet(Wallet wallet) {
        Wallet newWallet = Wallet.builder()
                .id(wallet.getId())
                .money(wallet.getMoney())
                .userAccount(wallet.getUserAccount())
                .build();
        walletList.add(newWallet);
        return Optional.of(newWallet);
    }

    public Optional<List<Wallet>> deleteWallet(Long id) {
        walletList.removeIf(wallet -> wallet.getId().equals(id));
        return Optional.of(walletList);
    }

    public Optional<Wallet> updateWallet(Wallet wallet) {
        Optional<Wallet> existingWallet = findWalletById(wallet.getId());
        existingWallet.ifPresent(foundWallet -> {
            foundWallet.setMoney(wallet.getMoney());
            foundWallet.setUserAccount(wallet.getUserAccount());
        });
        return existingWallet;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }
}
