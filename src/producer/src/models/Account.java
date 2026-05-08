package models;

public class Account {
    private int id;
    private String cpf;
    private String name;
    private long balance = 0;

    public Account(int id, String cpf, String name) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void deposit(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be greater than 0");
        }
        balance += value;
    }

    public void withdraw(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be greater than 0.");
        }
        if (balance < value) {
            throw new IllegalArgumentException("Balance must be greater than withdrawal.");
        }
        balance -= value;
    }
}
