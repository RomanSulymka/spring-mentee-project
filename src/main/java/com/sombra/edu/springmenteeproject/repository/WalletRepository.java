package com.sombra.edu.springmenteeproject.repository;

import com.sombra.edu.springmenteeproject.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findWalletByBalanceId(@Param("balance_id") Long balanceId);

}
