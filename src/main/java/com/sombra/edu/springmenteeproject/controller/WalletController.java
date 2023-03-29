package com.sombra.edu.springmenteeproject.controller;

import com.sombra.edu.springmenteeproject.entity.Wallet;
import com.sombra.edu.springmenteeproject.exception.NullEntityReferenceException;
import com.sombra.edu.springmenteeproject.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
    private final WalletService service;
    @Autowired
    public WalletController(WalletService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Optional<Wallet>> addWallet(@RequestBody Wallet wallet) throws NullEntityReferenceException {
        Optional<Wallet> newWallet = service.createNewWallet(wallet);
        return new ResponseEntity<>(newWallet, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> getAllWallets() {
        List<Wallet> wallets = service.getAllWallets();
        return new ResponseEntity<>(wallets, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Optional<Wallet>> updateWallet(@RequestBody Wallet wallet) throws NullEntityReferenceException {
        Optional<Wallet> updatedWallet = service.editWallet(wallet);
        return new ResponseEntity<>(updatedWallet, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> findWalletById(@PathVariable Long id) throws NoSuchElementException {
        Wallet wallet = service.findWalletById(id);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWallet(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
