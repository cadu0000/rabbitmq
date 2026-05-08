import config.EnvLoader;
import config.RabbitMqConfig;
import events.WithdrawalPublisher;
import models.Account;
import services.AccountService;

public class Main {
    public static void main(String[] args) throws Exception {
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

        long[] transactions = {100, -30, 50, -20, 200};

        for (int i = 0; i < transactions.length; i++) {
            try {
                long value = transactions[i];
                if (i % 2 == 0) {
                    accountService.deposit(account, value);
                } else {
                    accountService.withdraw(account, value);
                }
                Thread.sleep(500);
            } catch (Exception ignored) {
            }
        }

        try {
            accountService.withdraw(account, 5000);
        }  catch (Exception e) {
        System.out.println("(!) Transaction skipped: " + e.getMessage());
        }
    }
}