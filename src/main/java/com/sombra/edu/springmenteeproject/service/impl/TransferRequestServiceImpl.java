package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.TransferRequest;
import com.sombra.edu.springmenteeproject.repository.TransferRequestRepository;
import com.sombra.edu.springmenteeproject.service.TransferRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransferRequestServiceImpl implements TransferRequestService {

    private final TransferRequestRepository transferRequestRepository;

    @Autowired
    public TransferRequestServiceImpl(TransferRequestRepository transferRequestRepository) {
        this.transferRequestRepository = transferRequestRepository;
    }

    @Override
    public void saveTransferRequest(TransferRequest transferRequest) {
        transferRequestRepository.save(transferRequest);
        log.info("Transfer saved to database {}", transferRequest);
    }
}
