package com.sombra.edu.springmenteeproject.service;

import com.sombra.edu.springmenteeproject.dto.SendMoneyDTO;
import com.sombra.edu.springmenteeproject.entity.Money;

import java.util.List;

public interface MoneyService {
    List<Money> getAllMoney();

    List<SendMoneyDTO> getMoneyInformation();
}
