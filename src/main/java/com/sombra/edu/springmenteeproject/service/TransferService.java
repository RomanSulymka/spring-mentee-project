package com.sombra.edu.springmenteeproject.service;

import com.sombra.edu.springmenteeproject.dto.MoneyTransferDTO;
import com.sombra.edu.springmenteeproject.entity.Transfer;

public interface TransferService {
    Transfer sendMoneyToAnotherUser(MoneyTransferDTO transferDTO);
}
