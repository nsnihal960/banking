package banking;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import banking.api.controller.ProfileResource;
import banking.api.controller.StatementResource;
import banking.api.controller.TransactionResource;
import banking.api.controller.impl.ProfileResourceImpl;
import banking.api.controller.impl.StatementResourceImpl;
import banking.api.controller.impl.TransactionResouceImpl;
import banking.consumer.BankingResourceImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

@Singleton
public class BankingApplication extends Application<BankingConfiguration> {
    private final ProfileResource profileResource;
    private final StatementResource statementResource;
    private final TransactionResource transactionResource;

    @Inject
    public BankingApplication(ProfileResourceImpl profileResourceImpl,
                              StatementResourceImpl statementResourceImpl,
                              TransactionResouceImpl transactionResouceImpl) {
        this.profileResource = profileResourceImpl;
        this.statementResource = statementResourceImpl;
        this.transactionResource = transactionResouceImpl;
    }

    public static void main(final String[] args) throws Exception {
        Injector injector = Guice.createInjector(new BankingModule());
        injector.getInstance(BankingApplication.class).run(args);
    }

    @Override
    @GET
    @Path("/data")
    public String getName() {
        return "Banking";
    }

    @Override
    public void initialize(final Bootstrap<BankingConfiguration> bootstrap) {
    }

    @Override
    public void run(final BankingConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new BankingResourceImpl());
        environment.jersey().register(profileResource);
        environment.jersey().register(statementResource);
        environment.jersey().register(transactionResource);
    }

}
