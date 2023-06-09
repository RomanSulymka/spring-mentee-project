package com.sombra.edu.springmenteeproject.repository;

import com.sombra.edu.springmenteeproject.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
