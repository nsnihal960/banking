package banking;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import banking.consumer.BankingResource;
import banking.consumer.BankingResourceImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class bankingApplication extends Application<BankingConfiguration> {

    public static void main(final String[] args) throws Exception {
        new bankingApplication().run(args);
    }

    @Override
    @GET
    @Path("/data")
    public String getName() {
        return "Banking";
    }

    @Override
    public void initialize(final Bootstrap<BankingConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final BankingConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new BankingResourceImpl());
    }

}
