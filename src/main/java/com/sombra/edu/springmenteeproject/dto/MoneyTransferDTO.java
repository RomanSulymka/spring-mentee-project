package com.sombra.edu.springmenteeproject.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyTransferDTO {
    private Long senderAccountId;
    private Long receiverAccountId;
    private Long senderWalletId;
    private Long receiverWalletId;
    private BigDecimal quantity;
    private String currency;

}
