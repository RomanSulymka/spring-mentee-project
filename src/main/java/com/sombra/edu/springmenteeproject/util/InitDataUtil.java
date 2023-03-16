package com.sombra.edu.springmenteeproject.util;

import com.sombra.edu.springmenteeproject.entity.Money;
import com.sombra.edu.springmenteeproject.entity.UserAccount;
import com.sombra.edu.springmenteeproject.entity.Wallet;

import java.util.List;
import java.util.Random;

public class InitDataUtil {

    public static List<Wallet> initializeWallets(int numWallets, List<Wallet> walletList) {
        Random rand = new Random();

        for (int i = 0; i < numWallets; i++) {
            Money usdMoney = Money.builder()
                    .currency("USD")
                    .quantity(rand.nextDouble() * 1000)
                    .build();
            Money eurMoney = Money.builder()
                    .currency("EUR")
                    .quantity(rand.nextDouble() * 1000)
                    .build();

            Wallet wallet = Wallet.builder()
                    .id((long) i)
                    .money(List.of(usdMoney, eurMoney))
                    .userAccount(UserAccount.builder()
                            .id((long) i)
                            .firstName("First" + i)
                            .lastName("Last" + i)
                            .email("user" + i + "@example.com")
                            .password("password" + i)
                            .build())
                    .build();
            walletList.add(wallet);
        }

        return walletList;
    }
}
