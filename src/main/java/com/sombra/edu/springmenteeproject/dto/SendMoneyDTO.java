package com.sombra.edu.springmenteeproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendMoneyDTO {
    private Long id;
    private String walletName;
    private String moneyName;
    private String symbol;
    private Double balance;
}
