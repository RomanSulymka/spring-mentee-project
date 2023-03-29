package com.sombra.edu.springmenteeproject.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Money {
    private Long id;
    private String currency;
    private Double quantity;
}
