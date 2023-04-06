package com.sombra.edu.springmenteeproject.repository;

import com.sombra.edu.springmenteeproject.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query("SELECT b.balance FROM balances b INNER JOIN wallets w ON b.id = w.balance.id WHERE b.userAccount.id =:userId AND w.id =:walletId")
    Double findBalanceByUserAccountIdAndWalletId(@Param("userId") Long userId, @Param("walletId") Long walletId);

    @Modifying
    @Query("UPDATE balances b SET b.balance = b.balance + :quantity WHERE b.id = (SELECT balance.id FROM wallets WHERE id = :walletId)")
    void increaseMoneyByCurrencyAndWalletId(@Param("walletId") Long walletId,
                                              @Param("quantity") Double quantity);

    @Modifying
    @Query("UPDATE balances b SET b.balance = b.balance - :quantity WHERE b.id = (SELECT balance.id FROM wallets WHERE id = :walletId)")
    void subtractMoneyByCurrencyAndWalletId(@Param("walletId") Long walletId,
                                            @Param("quantity") Double quantity);

    @Query("SELECT b.money.symbol FROM balances b INNER JOIN Money m ON m.id = b.money.id INNER JOIN wallets w ON b.id = w.balance.id WHERE w.id = :walletId")
    String findCurrencyByWalletId(@Param("walletId") Long walletId);
}
