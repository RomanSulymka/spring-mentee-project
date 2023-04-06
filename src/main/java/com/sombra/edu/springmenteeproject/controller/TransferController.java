package com.sombra.edu.springmenteeproject.controller;

import com.sombra.edu.springmenteeproject.dto.MoneyTransferDTO;
import com.sombra.edu.springmenteeproject.entity.Transfer;
import com.sombra.edu.springmenteeproject.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Transfer> transferMoneyToAnotherAccount(@RequestBody MoneyTransferDTO transferDTO) throws NoSuchElementException {
        Transfer walletsByUserAccount = transferService.sendMoneyToAnotherUser(transferDTO);
        return new ResponseEntity<>(walletsByUserAccount, HttpStatus.OK);
    }
}
