package com.sombra.edu.springmenteeproject.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Wallet {
    private Long id;
    private List<Money> money;
    private UserAccount userAccount;
}
