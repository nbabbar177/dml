package com.booking.service.serviceImpl;

import com.booking.service.dto.response.DataResponseDto;
import com.booking.service.dto.response.TransactionResponseDto;
import com.booking.service.dto.response.SumDto;
import com.booking.service.dto.request.TransactionDto;
import com.booking.service.entity.TransactionEntity;
import com.booking.service.repository.TransactionRepository;
import com.booking.service.service.BookingService;
import com.booking.service.util.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public ResponseModel createTransaction(TransactionDto transactionDto,int transactionId) {
        Boolean check = transactionRepository.checkTransactionExists(transactionId);
        if(check!=null && !check){
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setTransaction_id(transactionId);
            transactionEntity.setAmount(transactionDto.getAmount());
            transactionEntity.setType(transactionDto.getType());
            transactionEntity.setCurrency(transactionDto.getCurrency());

            if(transactionDto.getParent()!=null){
                TransactionEntity parentTransaction = transactionRepository.findById(transactionDto.getParent()).orElse(null);

                if(parentTransaction != null){
                    if(parentTransaction.getCurrency().equals(transactionEntity.getCurrency())){
                        transactionEntity.setParent(parentTransaction);
                    }
                    else{
                        return new ResponseModel(new TransactionResponseDto("FAILED","Currency of parent doesn't match with child"), HttpStatus.BAD_REQUEST);
                    }
                }
                else {
                    return new ResponseModel(new TransactionResponseDto("FAILED","Invalid Parent Id"),HttpStatus.BAD_REQUEST);
                }
            }
            transactionRepository.save(transactionEntity);
            return new ResponseModel(new TransactionResponseDto("OK","Transaction Successfully Created!"),HttpStatus.OK);
        }
        else{
            return new ResponseModel(new TransactionResponseDto("FAILED","Transaction with Id :"+transactionId+" already exists"),HttpStatus.BAD_REQUEST);
        }




    }

    @Override
    public ResponseModel getTransactionIdList(String type) {
        String transactionType = type.toLowerCase();
        List<Integer> listOfTransactionId = transactionRepository.findTransactionIdByType(transactionType);
        if(listOfTransactionId.isEmpty()){
           return new ResponseModel(new TransactionResponseDto("FAILED","No Transaction Id found having type: "+type),HttpStatus.NOT_FOUND);
        }
        return new ResponseModel(new DataResponseDto<>(listOfTransactionId,"All the transaction ids having type: "+type), HttpStatus.OK);

    }

    @Override
    public ResponseModel getAllCurrencies() {
        List<String> listOfCurrencies = transactionRepository.findAllCurrencies();
        if(listOfCurrencies.isEmpty()){
            return new ResponseModel(new TransactionResponseDto("FAILED","No Currencies Found!"),HttpStatus.NOT_FOUND);
        }
        return new ResponseModel(new DataResponseDto<>(listOfCurrencies, "All the currencies used in the transaction."), HttpStatus.OK);
    }

    @Override
    public ResponseModel findSumOfTransactions(int transactionId) {

        TransactionEntity savedTransaction = transactionRepository.findById(transactionId).orElse(null);
        if(savedTransaction!=null){
            Set<TransactionEntity> childData =  savedTransaction.getChildren();

            double sum  = savedTransaction.getAmount();
            for (TransactionEntity data : childData) {
                sum += data.getAmount();
            }

            SumDto returnValue = new SumDto();
            returnValue.setSum(sum);
            returnValue.setCurrency(savedTransaction.getCurrency());

            return new ResponseModel(returnValue, HttpStatus.OK);
        }
        return new ResponseModel(new TransactionResponseDto("FAILED","No Transaction found with id: "+transactionId),HttpStatus.NOT_FOUND);

    }
}
