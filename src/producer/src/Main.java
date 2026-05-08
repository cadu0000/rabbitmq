import config.EnvLoader;
import events.RabbitMqConfig;
import events.WithdrawalPublisher;
import models.Account;
import services.AccountService;

public class Main {
    public static void main(String[] args)
            throws Exception {

        RabbitMqConfig config =
                EnvLoader.loadRabbitConfig();

        WithdrawalPublisher publisher =
                new WithdrawalPublisher(config);

        AccountService accountService =
                new AccountService(publisher);

        Account account =
                new Account(
                        1,
                        "11478294800",
                        "Lucas"
                );

        accountService.deposit(account, 300);
        accountService.withdraw(account, 250);
    }
}