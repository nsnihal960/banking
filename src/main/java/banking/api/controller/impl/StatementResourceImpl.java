package banking.api.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.core.Response;

import banking.api.controller.StatementResource;
import banking.api.dto.request.GetStatementRequest;
import banking.consumer.StatementConsumer;

@Singleton
public class StatementResourceImpl implements StatementResource {
    private final StatementConsumer statementConsumer;

    @Inject
    public StatementResourceImpl(StatementConsumer statementConsumer) {
        this.statementConsumer = statementConsumer;
    }

    @Override
    public Response getStatement(GetStatementRequest request) {
        request.validate();
        return Response.status(200)
                .entity(statementConsumer.getStatement(
                        request.id,
                        request.startTime,
                        request.endTime,
                        request.count
                ))
                .build();
    }
}
