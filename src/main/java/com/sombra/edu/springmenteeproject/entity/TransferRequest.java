package com.sombra.edu.springmenteeproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "transfer_requests")
public class TransferRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "sender_account_id", nullable = false)
    private UserAccount senderAccount;
    
    @ManyToOne
    @JoinColumn(name = "receiver_account_id", nullable = false)
    private UserAccount receiverAccount;

    private Long senderWalletId;

    private Long receiverWalletId;
    
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @OneToOne(mappedBy = "transferRequest")
    private Transfer transfer;

}