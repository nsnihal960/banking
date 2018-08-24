package banking.api.controller.impl;

import banking.consumer.AccountConsumer;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.core.Response;

import banking.api.controller.TransactionResource;
import banking.api.dto.request.AddBalanceRequest;
import banking.api.dto.request.DeductBalanceRequest;
import banking.api.dto.request.TransferBalanceRequest;
import banking.api.dto.response.Balance;

@Singleton
public class TransactionResouceImpl implements TransactionResource {
    private final AccountConsumer accountConsumer;

    @Inject
    public TransactionResouceImpl(AccountConsumer accountConsumer) {
        this.accountConsumer = accountConsumer;
    }

    @Override
    public Response addBalance(AddBalanceRequest addBalanceRequest) {
        addBalanceRequest.validate();
        Balance balance = accountConsumer.addBalance(
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
        deductBalanceRequest.validate();
        Balance balance = accountConsumer.deductBalance(
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
        transferBalanceRequest.validate();
        Balance balance = accountConsumer.transferMoney(
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
