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
}
