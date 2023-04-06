package com.sombra.edu.springmenteeproject.service.impl;

import com.sombra.edu.springmenteeproject.entity.TransferRequest;
import com.sombra.edu.springmenteeproject.repository.TransferRequestRepository;
import com.sombra.edu.springmenteeproject.service.TransferRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferRequestServiceImpl implements TransferRequestService {

    private final TransferRequestRepository transferRequestRepository;

    @Override
    public void saveTransferRequest(TransferRequest transferRequest) {
        transferRequestRepository.save(transferRequest);
        log.info("Transfer saved to database {}", transferRequest);
    }
}
