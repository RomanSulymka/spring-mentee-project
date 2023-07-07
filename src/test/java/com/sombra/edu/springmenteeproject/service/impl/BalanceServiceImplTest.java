package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.exception.NotFoundException;
import com.sombra.edu.springmenteeproject.repository.BalanceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BalanceServiceImplTest {

    @Mock
    private BalanceRepository balanceRepositoryMock;

    private BalanceServiceImpl balanceService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        balanceService = new BalanceServiceImpl(balanceRepositoryMock);
    }

    @Test
    public void testGetUserBalance() {
        BigDecimal balance = BigDecimal.valueOf(100);
        Mockito.when(balanceRepositoryMock.getBalanceByUserAccountIdAndWalletId(1L, 1L)).thenReturn(balance);

        BigDecimal result = balanceService.getUserBalance(1L, 1L);

        assertEquals(balance, result);
        Mockito.verify(balanceRepositoryMock).getBalanceByUserAccountIdAndWalletId(1L, 1L);
    }

    @Test(expected = NotFoundException.class)
    public void testGetUserBalanceNotFound() {
        Mockito.when(balanceRepositoryMock.getBalanceByUserAccountIdAndWalletId(1L, 1L)).thenReturn(null);

        balanceService.getUserBalance(1L, 1L);
    }

    @Test
    public void testIncreaseMoneyByCurrencyAndWalletId() {
        BigDecimal quantity = BigDecimal.valueOf(100);
        Mockito.doNothing().when(balanceRepositoryMock).increaseMoneyByCurrencyAndWalletId(1L, quantity);

        balanceService.increaseMoneyByCurrencyAndWalletId(quantity, 1L);

        Mockito.verify(balanceRepositoryMock).increaseMoneyByCurrencyAndWalletId(1L, quantity);
    }

    @Test
    public void testSubtractMoneyByCurrencyAndWalletId() {
        BigDecimal quantity = BigDecimal.valueOf(100);
        Mockito.doNothing().when(balanceRepositoryMock).subtractMoneyByCurrencyAndWalletId(1L, quantity);

        balanceService.subtractMoneyByCurrencyAndWalletId(quantity, 1L);

        Mockito.verify(balanceRepositoryMock).subtractMoneyByCurrencyAndWalletId(1L, quantity);
    }

    @Test
    public void testIsSameCurrencyInWalletsTrue() {
        Mockito.when(balanceRepositoryMock.getCurrencyByWalletId(1L)).thenReturn("USD");
        Mockito.when(balanceRepositoryMock.getCurrencyByWalletId(2L)).thenReturn("USD");

        boolean sameCurrency = balanceService.isSameCurrencyInWallets(1L, 2L);

        assertTrue(sameCurrency);
        Mockito.verify(balanceRepositoryMock).getCurrencyByWalletId(1L);
        Mockito.verify(balanceRepositoryMock).getCurrencyByWalletId(2L);
    }

    @Test
    public void testIsSameCurrencyInWalletsFalse() {
        Mockito.when(balanceRepositoryMock.getCurrencyByWalletId(1L)).thenReturn("USD");
        Mockito.when(balanceRepositoryMock.getCurrencyByWalletId(2L)).thenReturn("EUR");

        boolean sameCurrency = balanceService.isSameCurrencyInWallets(1L, 2L);

        assertFalse(sameCurrency);
        Mockito.verify(balanceRepositoryMock).getCurrencyByWalletId(1L);
        Mockito.verify(balanceRepositoryMock).getCurrencyByWalletId(2L);
    }
}
