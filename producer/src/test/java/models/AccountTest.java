package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {
    @Test
    void shouldDepositSuccessfully() {
        Account account = new Account(1, "12345678900", "Lucas");
        account.deposit(500);
        assertEquals(500, account.getBalance(), "The balance should be 500 after the deposit.");
    }

    @Test
    void shouldWithdrawSuccessfully() {
        Account account = new Account(1, "12345678900", "Lucas");
        account.deposit(500);
        account.withdraw(200);
        assertEquals(300, account.getBalance(), "The balance should be 300 after the deposit.");
    }

    @Test
    void shouldThrowExceptionWhenDepositIsZeroOrNegative() {
        Account account = new Account(1, "12345678900", "Lucas");

        IllegalArgumentException exceptionZero = assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(0)
        );
        assertEquals("Value must be greater than 0", exceptionZero.getMessage());

        IllegalArgumentException exceptionNegative = assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(-100)
        );
        assertEquals("Value must be greater than 0", exceptionNegative.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawIsZeroOrNegative() {
        Account account = new Account(1, "12345678900", "Lucas");

        IllegalArgumentException exceptionZero = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(0)
        );
        assertEquals("Value must be greater than 0.", exceptionZero.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawIsGreaterThanBalance() {
        Account account = new Account(1, "12345678900", "Lucas");
        account.deposit(100);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(150)
        );
        assertEquals("Balance must be greater than withdrawal.", exception.getMessage());
    }
}