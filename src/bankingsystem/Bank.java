package bankingsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Bank {
    private final Map<Integer, Account> accountList;
    private int accountId;

    public Bank() {
        this.accountList = new HashMap<Integer, Account>();
        this.accountId = 1;
    }

    public void saveAccount(Account account) {
        account.setId(accountId++);
        this.getAccountList().put(account.getId(), account);
    }

    public Account findAccountById(int id) {
        return this.getAccountList().entrySet().stream()
                .filter(entry -> entry.getKey() == id)
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    public List<Account> findAllAccounts() {
        return this.getAccountList().values().stream().toList();
    }

    public void updateAccount(Account account) throws Exception {
        if (this.getAccountList().containsKey(account.getId())) {
            this.getAccountList().put(account.getId(), account);
        }

        throw new Exception("Não foi possível atualizar a conta especificada.");
    }

    public void deleteAccount(int id) throws Exception {
        if (this.getAccountList().remove(id) == null) {
            throw new Exception("Não foi possível remover a conta especificada.");
        }
    }

    public Map<Integer, Account> getAccountList() {
        return accountList;
    }
}
