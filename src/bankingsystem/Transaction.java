package bankingsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final TransactionType transactionType;
    private final Account originAccount;
    private final Account destinationAccount;
    private final double value;
    private final LocalDateTime dateTime;

    public Transaction(TransactionType transactionType, Account originAccount, Account destinationAccount, double value) {
        this.transactionType = transactionType;
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.value = value;
        this.dateTime = LocalDateTime.now();
    }

    public Transaction(TransactionType transactionType, Account originAccount, double value) {
        this.transactionType = transactionType;
        this.originAccount = originAccount;
        this.destinationAccount = null;
        this.value = value;
        this.dateTime = LocalDateTime.now();
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Account getOriginAccount() {
        return originAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFormattedDateTime() {
        return this.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
