package racecondition;

public class BankAccount {
    private long balance;

    public BankAccount(long balance) {
        this.balance = balance;
    }

    public void withdraw(long amount) {
        long newBalance = balance - amount;
        balance = newBalance;
    }

    public void deposit(long amount) {
        long newBalance = balance + amount;
        balance = newBalance;
    }

    @Override
    public String toString() {
        return String.valueOf(balance);
    }
}
