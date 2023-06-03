package com.booking.service.service;

import com.booking.service.dto.response.SumDto;
import com.booking.service.dto.request.TransactionDto;
import com.booking.service.util.ResponseModel;


public interface BookingService {

    ResponseModel createTransaction(TransactionDto transactionDto, int transactionId);

    ResponseModel getTransactionIdList(String type);

    ResponseModel getAllCurrencies();

    ResponseModel findSumOfTransactions(int transactionId);



}
