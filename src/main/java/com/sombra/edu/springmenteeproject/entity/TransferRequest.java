package com.sombra.edu.springmenteeproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

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
    private Double amount;

    @OneToOne(mappedBy = "transferRequest")
    private Transfer transfer;

}