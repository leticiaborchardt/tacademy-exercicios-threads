package bankingsystem;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int id;
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(int id, String accountNumber, String accountHolderName, double balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.transactionHistory = new ArrayList<Transaction>();
    }

    public void deposit(double value, Account originAccount) {
        this.setBalance(this.getBalance() + value);

        this.addTransactionHistory(new Transaction(TransactionType.DEPOSIT, originAccount, this, value));
    }

    public void withdraw(double value, Account destinationAccount) throws Exception {
        this.withdrawBalance(value);

        this.addTransactionHistory(new Transaction(TransactionType.WITHDRAW, this, destinationAccount, value));
    }

    public void transfer(Account destinationAccount, double value) throws Exception {
        this.withdraw(value, destinationAccount);
        destinationAccount.deposit(value, this);
    }

    private void withdrawBalance(double value) throws Exception {
        if (this.getBalance() < value) {
            throw new Exception("Saldo insuficiente.");
        }

        this.setBalance(this.getBalance() - value);
    }

    public void showInformation() {
        System.out.println("Conta: " +
                "ID: " + this.getId() +
                "Número: " + this.getAccountNumber() +
                "Titular da conta:" + this.getAccountHolderName() +
                "Saldo: R$ " + this.getBalance()
        );
    }

    public void showTransactionHistory() {
        System.out.println("Histórico de transações:");

        this.getTransactionHistory().forEach((transaction) -> {
            System.out.println("Histórico de transações:" +
                    "\nTipo: " + transaction.getTransactionType().getDescription() +
                    "\nOrigem: " + transaction.getOriginAccount().getAccountNumber() + " - " + transaction.getOriginAccount().getAccountHolderName() +
                    "\nDestino:" + transaction.getDestinationAccount().getAccountNumber() + " - " + transaction.getDestinationAccount().getAccountHolderName() +
                    "\nValor: R$ " + transaction.getValue() +
                    "\nData/hora: " + transaction.getFormattedDateTime()
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

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public void addTransactionHistory(Transaction transaction) {
        this.getTransactionHistory().add(transaction);
    }
}
