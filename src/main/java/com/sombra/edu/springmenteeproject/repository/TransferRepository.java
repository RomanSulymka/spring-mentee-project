package com.sombra.edu.springmenteeproject.repository;

import com.sombra.edu.springmenteeproject.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
