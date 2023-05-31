package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.Balance;
import com.sombra.edu.springmenteeproject.entity.Wallet;
import com.sombra.edu.springmenteeproject.exception.ConflictException;
import com.sombra.edu.springmenteeproject.exception.NotFoundException;
import com.sombra.edu.springmenteeproject.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceImplTest {

    private WalletServiceImpl walletService;

    @Mock
    private WalletRepository walletRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        walletService = new WalletServiceImpl(walletRepository);
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

        verify(walletRepository, times(1)).existsById(wallet.getId());
        verify(walletRepository, times(1)).save(wallet);
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

        verify(walletRepository, times(1)).existsById(wallet.getId());
        verify(walletRepository, never()).save(wallet);
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

        verify(walletRepository, times(1)).existsById(wallet.getId());
        verify(walletRepository, times(1)).save(wallet);
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

        verify(walletRepository, times(1)).existsById(wallet.getId());
        verify(walletRepository, never()).save(wallet);
    }

    @Test
    void testGetWalletById_shouldReturnWallet_whenWalletExists() {
        Wallet wallet = Wallet.builder()
                .id(1L)
                .walletName("Test Wallet")
                .balance(new Balance())
                .build();

        when(walletRepository.existsById(wallet.getId())).thenReturn(true);

        Wallet retrievedWallet = walletService.getWalletById(wallet.getId());

        assertNotNull(retrievedWallet);
        assertEquals(wallet, retrievedWallet);

        verify(walletRepository, times(1)).existsById(wallet.getId());
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

        verify(walletRepository, times(1)).findById(wallet.getId());
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

        verify(walletRepository, times(1)).findAll();
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

        verify(walletRepository, times(1)).findById(wallet.getId());
        verify(walletRepository, times(1)).delete(wallet);
    }

    @Test
    void testDeleteShouldThrowExceptionWhenWalletDoesNotExist() {
        Long walletId = 1L;

        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> walletService.delete(walletId));

        verify(walletRepository, times(1)).findById(walletId);
        verify(walletRepository, never()).delete(any(Wallet.class));
    }
}
