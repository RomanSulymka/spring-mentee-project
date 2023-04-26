package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.Wallet;
import com.sombra.edu.springmenteeproject.exception.ConflictException;
import com.sombra.edu.springmenteeproject.exception.NotFoundException;
import com.sombra.edu.springmenteeproject.repository.WalletRepository;
import com.sombra.edu.springmenteeproject.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet createNewWallet(Wallet wallet) {
        if (walletRepository.existsById(wallet.getId())) {
            log.warn("Element is already exist");
            throw new ConflictException("Element with ID " + wallet.getId() + " already exists");
        }
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet editWallet(Wallet wallet) {
        if (!walletRepository.existsById(wallet.getId())) {
            log.warn("Can't find the element");
            throw new NotFoundException("Can't find the element");
        }
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet getWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow();
    }

    @Override
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public void delete(Long walletId) {
        Wallet wallet = getWalletById(walletId);
        walletRepository.delete(wallet);
        log.info("Element deleted {}", walletId);
    }

    @Override
    public boolean checkIsWalletsExist(Long senderWalletId, Long receiverWalletId) {
        return walletRepository.existsById(senderWalletId) && walletRepository.existsById(receiverWalletId);
    }

}
