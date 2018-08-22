package banking.api.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.core.Response;

import banking.api.controller.StatementResource;
import banking.consumer.StatementConsumer;

@Singleton
public class StatementResourceImpl implements StatementResource {
    private final StatementConsumer statementConsumer;

    @Inject
    public StatementResourceImpl(StatementConsumer statementConsumer) {
        this.statementConsumer = statementConsumer;
    }

    @Override
    public Response getStatement(String id) {
        return Response.status(200)
                .entity(statementConsumer.getStatement(id))
                .build();
    }
}
