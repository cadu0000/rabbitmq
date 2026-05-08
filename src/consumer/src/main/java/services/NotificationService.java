package services;

import dtos.TransactionEventDTO;
import dtos.TransactionType;

public class NotificationService {

    public void notify(TransactionEventDTO event) {

        String actionMessage = event.type() == TransactionType.DEPOSIT
                ? "Deposit completed."
                : "Withdrawal completed.";

        String notification = String.format(
                "\n======= NOTIFICATION =======\n" +
                        "Account ID: %s\n" +
                        "Account Name: %s\n" +
                        "Occurred At: %s\n" +
                        "%s\n" +
                        "Amount: %s\n" +
                        "Current Balance: %s\n" +
                        "================================\n",
                event.accountId(),
                event.accountName(),
                event.occurredAt(),
                actionMessage,
                formatMoney(event.amount()),
                formatMoney(event.balance())
        );

        System.out.print(notification);
    }

    private String formatMoney(long cents) {
        return String.format("R$ %.2f", cents / 100.0);
    }
}