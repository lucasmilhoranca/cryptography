package tech.run.crypto.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import tech.run.crypto.controller.dto.CreateTransactionRequest;
import tech.run.crypto.controller.dto.TransactionResponse;
import tech.run.crypto.controller.dto.UpdateTransactionRequest;
import tech.run.crypto.entity.TransactionEntity;
import tech.run.crypto.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void create(CreateTransactionRequest request) {
        var entity = new TransactionEntity();

        entity.setRawCreditCardToken(request.creditCardToken());
        entity.setRawUserDocument(request.userDocument());
        entity.setTransactionValue(request.value());

        transactionRepository.save(entity);
    }

    public TransactionResponse findById(Long id) {
        var entity = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return TransactionResponse.fromEntity(entity);
    }

    public Page<TransactionResponse> listAll(int page, int pageSzie) {
        var content = transactionRepository.findAll(PageRequest.of(page, pageSzie));

        return content.map(TransactionResponse::fromEntity);
    }

    public void update(Long id, UpdateTransactionRequest request) {
        var entity = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        entity.setTransactionValue(request.value());

        transactionRepository.save(entity);
    }

    public void deleteById(Long id) {
        var entity = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        transactionRepository.deleteById(entity.getTransactionId());
    }
}
