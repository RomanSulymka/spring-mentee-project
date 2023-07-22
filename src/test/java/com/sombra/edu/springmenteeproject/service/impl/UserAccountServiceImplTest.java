package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.Role;
import com.sombra.edu.springmenteeproject.entity.UserAccount;
import com.sombra.edu.springmenteeproject.repository.UserAccountRepository;
import com.sombra.edu.springmenteeproject.service.UserAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserAccountServiceImplTest {

    UserAccountService userAccountService;

    @Mock
    UserAccountRepository userAccountRepository;

    public static Object[][] provideUserAccountsForGetAccountByIdTest() {
        return new Object[][]{
                {1L, "John", "Doe", "john@example.com", "password", Role.USER},
                {2L, "Jane", "Smith", "jane@example.com", "password", Role.ADMIN},
                {3L, "Bob", "Johnson", "bob@example.com", "password", Role.USER}
        };
    }

    public static Long[][] provideUserAccountsIds() {
        return new Long[][] {
                {1L, 3L},
                {2L, 13L}
        };
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userAccountService = new UserAccountServiceImpl(userAccountRepository);
    }

    @Test
    void testCheckIsUserAccountExist_BothAccountsExist_ReturnsTrue() {
        Long senderAccountId = 1L;
        Long receiverAccountId = 2L;
        when(userAccountRepository.existsById(senderAccountId)).thenReturn(true);
        when(userAccountRepository.existsById(receiverAccountId)).thenReturn(true);

        boolean result = userAccountService.checkIsUserAccountExist(senderAccountId, receiverAccountId);

        Assertions.assertTrue(result);
        verify(userAccountRepository, times(1)).existsById(senderAccountId);
        verify(userAccountRepository, times(1)).existsById(receiverAccountId);
    }

    @ParameterizedTest
    @MethodSource("provideUserAccountsIds")
    void testCheckIsUserAccountExist_SenderAccountDoesNotExist_ReturnsFalse(Long firstUser, Long secondUser) {
        Long senderAccountId = firstUser;
        Long receiverAccountId = secondUser;
        when(userAccountRepository.existsById(senderAccountId)).thenReturn(false);
        when(userAccountRepository.existsById(receiverAccountId)).thenReturn(true);

        boolean result = userAccountService.checkIsUserAccountExist(senderAccountId, receiverAccountId);

        Assertions.assertFalse(result);
    }

    @Test
    void testCheckIsUserAccountExist_ReceiverAccountDoesNotExist_ReturnsFalse() {
        Long senderAccountId = 1L;
        Long receiverAccountId = 2L;
        when(userAccountRepository.existsById(senderAccountId)).thenReturn(true);
        when(userAccountRepository.existsById(receiverAccountId)).thenReturn(false);

        boolean result = userAccountService.checkIsUserAccountExist(senderAccountId, receiverAccountId);

        Assertions.assertFalse(result);
        verify(userAccountRepository, times(1)).existsById(senderAccountId);
        verify(userAccountRepository, times(1)).existsById(receiverAccountId);
    }

    @ParameterizedTest
    @MethodSource("provideUserAccountsForGetAccountByIdTest")
    void testGetAccountById_UserAccountExists_ReturnsUserAccount(Long id, String firstName, String lastName, String email, String password, Role role) {
        UserAccount userAccount = UserAccount.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .role(role)
                .build();
        when(userAccountRepository.findById(id)).thenReturn(Optional.of(userAccount));

        UserAccount result = userAccountService.getAccountById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(userAccount, result);
        verify(userAccountRepository, times(1)).findById(id);
    }

    @Test
    void testGetAccountById_UserAccountDoesNotExist_ThrowsNotFoundException() {
        Long userId = 1L;
        when(userAccountRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> userAccountService.getAccountById(userId));
        verify(userAccountRepository, times(1)).findById(userId);
    }
}
