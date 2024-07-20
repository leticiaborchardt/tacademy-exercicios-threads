package bankingsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int id;
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private List<Transaction> transactionHistory;
    private final ReentrantLock transactionsLock;

    public Account(int id, String accountNumber, String accountHolderName, double balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.transactionHistory = new ArrayList<Transaction>();
        this.transactionsLock = new ReentrantLock();
    }

    public void deposit(double value, Account originAccount) {
        this.getTransactionsLock().lock();

        try {
            this.setBalance(this.getBalance() + value);
            this.addTransactionHistory(new Transaction(TransactionType.DEPOSIT, originAccount, this, value));
        } finally {
            this.getTransactionsLock().unlock();
        }
    }

    public void withdraw(double value) throws Exception {
        this.getTransactionsLock().lock();

        try {
            this.withdrawBalance(value);
            this.addTransactionHistory(new Transaction(TransactionType.WITHDRAW, this, value));
        } finally {
            this.getTransactionsLock().unlock();
        }
    }

    public void transfer(Account destinationAccount, double value) throws Exception {
        this.getTransactionsLock().lock();

        try {
            this.withdrawBalance(value);
            this.addTransactionHistory(new Transaction(TransactionType.TRANSFER, this, destinationAccount, value));
        } finally {
            this.getTransactionsLock().unlock();
        }

        destinationAccount.deposit(value, this);
    }

    private void withdrawBalance(double value) throws Exception {
        if (this.getBalance() < value) {
            throw new Exception("Saldo insuficiente.");
        }

        this.setBalance(this.getBalance() - value);
    }

    public void showInformation() {
        System.out.println(
                "\n\n----------- DADOS DA CONTA ----------" +
                        "\nID: " + this.getId() +
                        "\nNúmero: " + this.getAccountNumber() +
                        "\nTitular da conta:" + this.getAccountHolderName() +
                        "\nSaldo: R$ " + this.getBalance()
        );
    }

    public void showTransactionHistory() {
        System.out.println("\n------ HISTÓRICO DE TRANSAÇÕES ------");

        this.getTransactionHistory().forEach((transaction) -> {
            System.out.println(
                    "\nTipo: " + transaction.getTransactionType().getDescription() +
                            "\nOrigem: " + transaction.getOriginAccount().getAccountHolderName() + " (ID: " + transaction.getOriginAccount().getId() + ")" +
                            (
                                    transaction.getDestinationAccount() != null
                                            ? "\nDestino: " + transaction.getDestinationAccount().getAccountHolderName() + " (ID: " + transaction.getDestinationAccount().getId() + ")"
                                            : ""
                            ) +
                            "\nValor: R$ " + transaction.getValue() +
                            "\nData/hora: " + transaction.getFormattedDateTime() +
                            "\n--------------------------------------"
            );
        });
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        this.getTransactionsLock().lock();
        
        try {
            return balance;
        } finally {
            this.getTransactionsLock().unlock();
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransactionHistory(Transaction transaction) {
        this.getTransactionHistory().add(transaction);
    }

    public ReentrantLock getTransactionsLock() {
        return transactionsLock;
    }
}
