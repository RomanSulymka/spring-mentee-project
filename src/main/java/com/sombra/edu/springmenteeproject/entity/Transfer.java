package com.sombra.edu.springmenteeproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transfer_request_id")
    private TransferRequest transferRequest;

    private Long senderWalletId;

    private Long receiverWalletId;

}
