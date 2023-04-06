package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.Wallet;
import com.sombra.edu.springmenteeproject.repository.WalletRepository;
import com.sombra.edu.springmenteeproject.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet createNewWallet(Wallet wallet) {
        if (walletRepository.existsById(wallet.getId())) {
            log.warn("Element is already exist");
            throw new IllegalArgumentException("Element is already exist");
        }
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet editWallet(Wallet wallet) {
        if (!walletRepository.existsById(wallet.getId())) {
            log.warn("Can't find the element");
            throw new IllegalArgumentException("Can't find the element");
        }
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow();
    }

    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public void delete(Long walletId) {
        Wallet wallet = findWalletById(walletId);
        walletRepository.delete(wallet);
        log.warn("Element deleted");
    }

    @Override
    public boolean checkIsWalletsExist(Long senderWalletId, Long receiverWalletId) {
        return walletRepository.existsById(senderWalletId) && walletRepository.existsById(receiverWalletId);
    }

}
