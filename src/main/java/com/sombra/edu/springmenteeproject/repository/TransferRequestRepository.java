package com.sombra.edu.springmenteeproject.repository;

import com.sombra.edu.springmenteeproject.entity.TransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRequestRepository extends JpaRepository<TransferRequest, Long> {
}
