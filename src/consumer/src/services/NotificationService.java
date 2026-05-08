package services;

import dtos.TransactionEventDTO;
import dtos.TransactionType;

public class NotificationService {

    public void notify(
            TransactionEventDTO event
    ) {

        System.out.println(
                "\n======= NOTIFICATION ======="
        );

        System.out.println(
                "Account ID: "
                        + event.accountId()
        );

        System.out.println(
                "Account Name: "
                        + event.accountName()
        );

        System.out.println(
                "Occurred At: "
                        + event.occurredAt()
        );

        if (event.type()
                == TransactionType.DEPOSIT) {

            System.out.println(
                    "Deposit completed."
            );

        } else {

            System.out.println(
                    "Withdrawal completed."
            );
        }

        System.out.println(
                "Amount: "
                        + formatMoney(event.amount())
        );

        System.out.println(
                "Current Balance: "
                        + formatMoney(event.balance())
        );

        System.out.println(
                "================================"
        );
    }

    private String formatMoney(
            long cents
    ) {

        return String.format(
                "R$ %.2f",
                cents / 100.0
        );
    }
}