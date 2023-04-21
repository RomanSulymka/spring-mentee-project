package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.dto.MoneyTransferDTO;
import com.sombra.edu.springmenteeproject.entity.Status;
import com.sombra.edu.springmenteeproject.entity.Transfer;
import com.sombra.edu.springmenteeproject.entity.TransferRequest;
import com.sombra.edu.springmenteeproject.exception.CurrencyMismatchException;
import com.sombra.edu.springmenteeproject.exception.LowBalanceException;
import com.sombra.edu.springmenteeproject.exception.NotFoundException;
import com.sombra.edu.springmenteeproject.repository.TransferRepository;
import com.sombra.edu.springmenteeproject.service.BalanceService;
import com.sombra.edu.springmenteeproject.service.TransferRequestService;
import com.sombra.edu.springmenteeproject.service.TransferService;
import com.sombra.edu.springmenteeproject.service.UserAccountService;
import com.sombra.edu.springmenteeproject.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final UserAccountService userAccountService;
    private final WalletService walletService;
    private final BalanceService balanceService;
    private final TransferRequestService transferRequestService;

    @Transactional
    @Override
    public Transfer sendMoneyToAnotherUser(MoneyTransferDTO transferDTO) {
        TransferRequest transferRequest = getTransferRequest(transferDTO);
        if (!isValidTransfer(transferDTO)) {
            processTransferRequest(transferRequest, Status.FAILED);
            throw new IllegalArgumentException("Invalid transfer request");
        }
        balanceService.subtractMoneyByCurrencyAndWalletId(transferDTO.getQuantity(), transferDTO.getSenderWalletId());
        balanceService.increaseMoneyByCurrencyAndWalletId(transferDTO.getQuantity(), transferDTO.getReceiverWalletId());
        return processTransferRequest(transferRequest, Status.SUCCESS);
    }

    private boolean isValidTransfer(MoneyTransferDTO transferDTO) {
        if (!userAccountService.checkIsUserAccountExist(transferDTO.getSenderAccountId(), transferDTO.getReceiverAccountId())) {
            throw new NotFoundException("Account is not exist");
        }
        if (!walletService.checkIsWalletsExist(transferDTO.getSenderWalletId(), transferDTO.getReceiverWalletId())) {
            throw new NotFoundException("Wallet is not exist");
        }
        if (!balanceService.isSameCurrencyInWallets(transferDTO.getSenderWalletId(), transferDTO.getReceiverWalletId())) {
            throw new CurrencyMismatchException("Currency in wallets is not the same");
        }
        BigDecimal senderBalance = balanceService.getUserBalance(transferDTO.getSenderAccountId(), transferDTO.getSenderWalletId());
        if (senderBalance.compareTo(transferDTO.getQuantity()) < 0) {
            throw new LowBalanceException("The user does not have enough money in the account");
        }
        return true;
    }

    private Transfer processTransferRequest(TransferRequest transferRequest, Status status) {
        Transfer transfer = Transfer.builder()
                .status(status)
                .transactionDate(LocalDateTime.now())
                .transferRequest(transferRequest)
                .senderWalletId(transferRequest.getSenderWalletId())
                .receiverWalletId(transferRequest.getReceiverWalletId())
                .build();
        transferRepository.save(transfer);
        transferRequestService.saveTransferRequest(transferRequest);
        return transfer;
    }

    private TransferRequest getTransferRequest(MoneyTransferDTO transferDTO) {
        return TransferRequest.builder()
                .senderAccount(userAccountService.getAccountById(transferDTO.getSenderAccountId()))
                .receiverAccount(userAccountService.getAccountById(transferDTO.getReceiverAccountId()))
                .amount(transferDTO.getQuantity())
                .senderWalletId(transferDTO.getSenderWalletId())
                .receiverWalletId(transferDTO.getReceiverWalletId())
                .build();
    }

}
