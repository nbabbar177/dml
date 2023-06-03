package com.booking.service.controller;

import com.booking.service.dto.request.TransactionDto;
import com.booking.service.repository.TransactionRepository;
import com.booking.service.service.BookingService;
import com.booking.service.util.ResponseModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookingservice")
public class TransactionController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TransactionRepository transactionRepository;

    @PutMapping("/transaction/{transaction_id}")
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionDto transactionDto, @PathVariable("transaction_id") int transactionId){
        ResponseModel returnValue = bookingService.createTransaction(transactionDto,transactionId);

        return new ResponseEntity<>(returnValue.getData(),returnValue.getHttpStatus());
    }

    @GetMapping("/types/{type}")
    public ResponseEntity<?> getTransactionIdsWithGivenType(@PathVariable("type") String type){
        ResponseModel returnValue = bookingService.getTransactionIdList(type);

        return new ResponseEntity<>(returnValue.getData(), returnValue.getHttpStatus());
    }

    @GetMapping("/currencies")
    public ResponseEntity<?> getAllUsedCurrencies(){
        ResponseModel returnValue = bookingService.getAllCurrencies();

        return new ResponseEntity<>(returnValue.getData(), returnValue.getHttpStatus());
    }

    @GetMapping("/sum/{transaction_id}")
    public ResponseEntity<?> findSumOfTransactions(@PathVariable("transaction_id") int transactionId){
        ResponseModel returnValue = bookingService.findSumOfTransactions(transactionId);

        return new ResponseEntity<>(returnValue.getData(), returnValue.getHttpStatus());
    }


}
