package com.sombra.edu.springmenteeproject.dto;

import lombok.Data;

@Data
public class MoneyTransferDTO {
    private Long senderAccountId;
    private Long receiverAccountId;
    private Long senderWalletId;
    private Long receiverWalletId;
    private Double quantity;
    private String currency;

}
