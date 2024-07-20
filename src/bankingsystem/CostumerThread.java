package bankingsystem;

import java.util.Random;

public class CostumerThread extends Thread {
    private final Bank bank;
    private final int originAccountId;
    private final int destinationAccountId;
    private final TransactionType transactionType;
    private final double value;

    public CostumerThread(Bank bank, int originAccountId, int destinationAccountId, TransactionType transactionType, double value) {
        this.bank = bank;
        this.originAccountId = originAccountId;
        this.destinationAccountId = destinationAccountId;
        this.transactionType = transactionType;
        this.value = value;
    }

    @Override
    public void run() {
        super.run();

        Account account = this.getBank().findAccountById(this.getOriginAccountId());

        try {
            Random random = new Random();
            Thread.sleep(1000 * (random.nextInt(10) + 1));

            System.out.println("Realizando transação na conta ID " + account.getId());

            switch (this.getTransactionType()) {
                case TransactionType.DEPOSIT -> account.deposit(this.getValue(), account);
                case TransactionType.WITHDRAW -> account.withdraw(this.getValue());
                case TransactionType.TRANSFER -> account.transfer(this.getBank().findAccountById(this.getDestinationAccountId()), this.getValue());
                default -> System.out.println("Opção de transação inválida!");
            }

            System.out.println("Transação concluída na conta ID " + account.getId());
        } catch (Exception e) {
            System.out.println("Erro na transação da conta ID " + account.getId() + ": " + e.getMessage());
        }
    }

    public Bank getBank() {
        return bank;
    }

    public int getOriginAccountId() {
        return originAccountId;
    }

    public int getDestinationAccountId() {
        return destinationAccountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getValue() {
        return value;
    }
}
