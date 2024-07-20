package bankingsystem;

import java.util.ArrayList;
import java.util.List;

public class BankingSystem {
    public static void run() {
        Bank bank = new Bank();

        bank.saveAccount(new Account(1, "1234-5678-9101", "João", 1000));
        bank.saveAccount(new Account(2, "0001-4689-3325", "Maria", 1500));
        bank.saveAccount(new Account(3, "8569-1597-7536", "Luiza", 850));
        bank.saveAccount(new Account(4, "4552-6591-0201", "Carla", 748));
        bank.saveAccount(new Account(5, "1115-7778-0055", "Lucas", 400));

        List<CostumerThread> threads = new ArrayList<CostumerThread>();
        threads.add(new CostumerThread(bank, 1, 1, TransactionType.DEPOSIT, 100));
        threads.add(new CostumerThread(bank, 1, 2, TransactionType.TRANSFER, 360));
        threads.add(new CostumerThread(bank, 2, 2, TransactionType.WITHDRAW, 1550.50));
        threads.add(new CostumerThread(bank, 3, 3, TransactionType.DEPOSIT, 899.90));
        threads.add(new CostumerThread(bank, 5, 5, TransactionType.WITHDRAW, 40000));
        threads.add(new CostumerThread(bank, 4, 1, TransactionType.TRANSFER, 126.75));

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        bank.findAccountById(1).showInformation();
        bank.findAccountById(1).showTransactionHistory();

        bank.findAccountById(2).showInformation();
        bank.findAccountById(2).showTransactionHistory();

        bank.findAccountById(3).showInformation();
        bank.findAccountById(3).showTransactionHistory();
    }
}
