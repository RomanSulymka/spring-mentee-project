package com.sombra.edu.springmenteeproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String walletName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "balance_id", unique = true)
    private Balance balance;

}
