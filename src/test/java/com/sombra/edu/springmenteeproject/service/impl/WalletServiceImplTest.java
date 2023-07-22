package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.Balance;
import com.sombra.edu.springmenteeproject.entity.Wallet;
import com.sombra.edu.springmenteeproject.exception.ConflictException;
import com.sombra.edu.springmenteeproject.exception.NotFoundException;
import com.sombra.edu.springmenteeproject.repository.WalletRepository;
import com.sombra.edu.springmenteeproject.service.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class WalletServiceImplTest {

    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    public static Object[][] testData() {
        return new Object[][]{
                {1L, "USD", new Balance()},
                {2L, "EUR", new Balance()},
                {3L, "PLZ", new Balance()}
        };
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        walletService = new WalletServiceImpl(walletRepository);
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testGetWalletById(Long id, String walletName, Balance balance) {
        when(walletRepository.findById(id)).thenReturn(Optional.ofNullable(Wallet.builder()
                .id(id)
                .walletName(walletName)
                .balance(new Balance())
                .build()));

        Wallet testWallet = new Wallet(id, walletName, balance);
        Wallet result = walletService.getWalletById(id);
        Assertions.assertEquals(testWallet, result);

    }


    @Test
    void testCreateNewWalletShouldSaveWalletWhenWalletDoesNotExist() {
        Wallet wallet = Wallet.builder()
                .id(1L)
                .walletName("Test wallet")
                .balance(new Balance())
                .build();

        when(walletRepository.existsById(wallet.getId())).thenReturn(false);
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet savedWallet = walletService.createNewWallet(wallet);

        assertNotNull(savedWallet);
        assertEquals(wallet, savedWallet);

        Mockito.verify(walletRepository, times(1)).existsById(wallet.getId());
        Mockito.verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void testCreateNewWalletShouldThrowConflictExceptionWhenWalletAlreadyExists() {
        Wallet wallet = Wallet.builder()
                .id(1L)
                .walletName("Test Wallet")
                .balance(new Balance())
                .build();

        when(walletRepository.existsById(wallet.getId())).thenReturn(true);

        assertThrows(ConflictException.class, () -> walletService.createNewWallet(wallet));

        Mockito.verify(walletRepository, times(1)).existsById(wallet.getId());
        Mockito.verify(walletRepository, never()).save(wallet);
    }

    @Test
    void testEditWallet_shouldSaveEditedWallet_whenWalletExists() {
        Wallet wallet = Wallet.builder()
                .id(1L)
                .walletName("Test Wallet")
                .balance(new Balance())
                .build();

        when(walletRepository.existsById(wallet.getId())).thenReturn(true);
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet editedWallet = walletService.editWallet(wallet);

        assertNotNull(editedWallet);
        assertEquals(wallet, editedWallet);

        Mockito.verify(walletRepository, times(1)).existsById(wallet.getId());
        Mockito.verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void testEditWalletShouldThrowNotFoundExceptionWhenWalletDoesNotExist() {
        Wallet wallet = Wallet.builder()
                .id(1L)
                .walletName("Test Wallet")
                .balance(new Balance())
                .build();

        when(walletRepository.existsById(wallet.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> walletService.editWallet(wallet));

        Mockito.verify(walletRepository, times(1)).existsById(wallet.getId());
        Mockito.verify(walletRepository, never()).save(wallet);
    }

    @Test
    void getWalletById_shouldThrowException_whenWalletDoesNotExist() {
        Wallet wallet = Wallet.builder()
                .id(1L)
                .walletName("Test Wallet")
                .balance(new Balance())
                .build();

        when(walletRepository.findById(wallet.getId())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> walletService.getWalletById(wallet.getId()));

        Mockito.verify(walletRepository, times(1)).findById(wallet.getId());
    }

    @Test
    void testGetAllWallets_shouldReturnAllWallets() {
        List<Wallet> wallets = new ArrayList<>();
        Wallet wallet1 = Wallet.builder()
                .id(1L)
                .walletName("Wallet 1")
                .balance(new Balance())
                .build();
        Wallet wallet2 = Wallet.builder()
                .id(2L)
                .walletName("Wallet 2")
                .balance(new Balance())
                .build();
        wallets.add(wallet1);
        wallets.add(wallet2);

        when(walletRepository.findAll()).thenReturn(wallets);

        List<Wallet> retrievedWallets = walletService.getAllWallets();

        assertNotNull(retrievedWallets);
        assertEquals(wallets.size(), retrievedWallets.size());
        assertEquals(wallets, retrievedWallets);

        Mockito.verify(walletRepository, times(1)).findAll();
    }

    @Test
    void testDeleteShouldDeleteWalletWhenWalletExists() {
        Wallet wallet = Wallet.builder()
                .id(1L)
                .walletName("Test Wallet")
                .balance(new Balance())
                .build();

        when(walletRepository.findById(wallet.getId())).thenReturn(Optional.of(wallet));

        walletService.delete(wallet.getId());

        Mockito.verify(walletRepository, times(1)).findById(wallet.getId());
        Mockito.verify(walletRepository, times(1)).delete(wallet);
    }

    @Test
    void testDeleteShouldThrowExceptionWhenWalletDoesNotExist() {
        Long walletId = 1L;

        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> walletService.delete(walletId));

        Mockito.verify(walletRepository, times(1)).findById(walletId);
        Mockito.verify(walletRepository, never()).delete(any(Wallet.class));
    }
}
