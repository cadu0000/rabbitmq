package services;

import dtos.TransactionEventDTO;
import dtos.TransactionType;
import events.WithdrawalPublisher;
import models.Account;

import java.time.Instant;

public class AccountService {
    private final WithdrawalPublisher publisher;

    public AccountService(
            WithdrawalPublisher publisher
    ) {
        this.publisher = publisher;
    }

    public void withdraw(
            Account account,
            int amount
    ) throws Exception {
        account.withdraw(amount);

        TransactionEventDTO event =
                new TransactionEventDTO(
                        account.getId(),
                        account.getName(),
                        TransactionType.WITHDRAW,
                        amount,
                        account.getBalance(),
                        Instant.now()
                );
        publisher.publish(event);

        System.out.println(
                "Withdrawal completed successfully!"
        );
    }

    public void deposit(
            Account account,
            long amount
    ) throws Exception {

        account.deposit(amount);

        TransactionEventDTO event =
                new TransactionEventDTO(
                        account.getId(),
                        account.getName(),
                        TransactionType.DEPOSIT,
                        amount,
                        account.getBalance(),
                        Instant.now()
                );

        publisher.publish(event);

        System.out.println(
                "Deposit completed successfully!"
        );
    }
}