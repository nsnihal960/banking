package banking.api.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.core.Response;

import banking.api.controller.TransactionResource;
import banking.api.dto.request.AddBalanceRequest;
import banking.api.dto.request.DeductBalanceRequest;
import banking.api.dto.request.TransferBalanceRequest;
import banking.api.dto.response.Balance;
import banking.consumer.TransactionConsumer;

@Singleton
public class TransactionResouceImpl implements TransactionResource {
    private final TransactionConsumer transactionConsumer;

    @Inject
    public TransactionResouceImpl(TransactionConsumer transactionConsumer) {
        this.transactionConsumer = transactionConsumer;
    }

    @Override
    public Response addBalance(AddBalanceRequest addBalanceRequest) {
        Balance balance =  transactionConsumer.addBalance(
                addBalanceRequest.userId,
                addBalanceRequest.amount,
                addBalanceRequest.currency
        );
        return Response.status(200)
                .entity(balance)
                .build();
    }

    @Override
    public Response deductBalance(DeductBalanceRequest deductBalanceRequest) {
        Balance balance = transactionConsumer.deductBalance(
                deductBalanceRequest.userId,
                deductBalanceRequest.amount,
                deductBalanceRequest.currency
        );
        return Response.status(200)
                .entity(balance)
                .build();
    }

    @Override
    public Response transferBalance(TransferBalanceRequest transferBalanceRequest) {
        Balance balance = transactionConsumer.transferMoney(
                transferBalanceRequest.fromUserId,
                transferBalanceRequest.toUserId,
                transferBalanceRequest.amount,
                transferBalanceRequest.currency
        );
        return Response.status(200)
                .entity(balance)
                .build();
    }
}
