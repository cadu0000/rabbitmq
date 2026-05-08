package dtos;

import java.time.Instant;

public record TransactionEventDTO(
        int accountId,
        String accountName,
        TransactionType type,
        long amount,
        long balance,
        Instant occurredAt
) {
}