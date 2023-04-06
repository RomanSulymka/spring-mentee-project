package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.dto.MoneyTransferDTO;
import com.sombra.edu.springmenteeproject.entity.Status;
import com.sombra.edu.springmenteeproject.entity.Transfer;
import com.sombra.edu.springmenteeproject.entity.TransferRequest;
import com.sombra.edu.springmenteeproject.repository.TransferRepository;
import com.sombra.edu.springmenteeproject.service.*;
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
            getTransfer(transferRequest, Status.FAILED);
            throw new IllegalArgumentException("Invalid transfer request");
        }
        balanceService.subtractMoneyByCurrencyAndWalletId(transferDTO.getQuantity(), transferDTO.getSenderWalletId());
        balanceService.increaseMoneyByCurrencyAndWalletId(transferDTO.getQuantity(), transferDTO.getReceiverWalletId());
        return getTransfer(transferRequest, Status.SUCCESS);
    }

    private boolean isValidTransfer(MoneyTransferDTO transferDTO) {
        if (!userAccountService.checkIsUserAccountExist(transferDTO.getSenderAccountId(), transferDTO.getReceiverAccountId())) {
            log.error("Account is not exist");
            return false;
        }
        if (!walletService.checkIsWalletsExist(transferDTO.getSenderWalletId(), transferDTO.getReceiverWalletId())) {
            log.error("Wallet is not exist");
            return false;
        }
        if (!balanceService.isSameCurrencyInWallets(transferDTO.getSenderWalletId(), transferDTO.getReceiverWalletId())) {
            return false;
        }
        BigDecimal senderBalance = balanceService.getUserBalance(transferDTO.getSenderAccountId(), transferDTO.getSenderWalletId());
        if (senderBalance.compareTo(transferDTO.getQuantity()) < 0) {
            log.error("The user does not have enough money in the account");
            return false;
        }
        return true;
    }

    private Transfer getTransfer(TransferRequest transferRequest, Status status) {
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
