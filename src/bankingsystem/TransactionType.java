package bankingsystem;

public enum TransactionType {
    DEPOSIT("Depósito"),
    WITHDRAW("Saque"),
    TRANSFER("Transferência");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
