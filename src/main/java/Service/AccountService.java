package Service;

import Model.Account;
import DAO.AccountDao;

public class AccountService {
    
    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account addUserAccount(Account account) {
        if (account.getUsername().length() > 0 &&
            account.getPassword().length() >= 4 &&
            accountDao.getAccountByUsername(account.getUsername()) == null) {
                return accountDao.insertUser(account);
        }
        return null;
    }

    public Account processUserLogin(Account account) {
        Account retrievedAccount = accountDao.getAccountByUsername(account.getUsername());
        
        if (retrievedAccount != null &&
            retrievedAccount.getPassword().equals(account.getPassword())) {
                return retrievedAccount;
        }
        return null;
    }

    public Account getAccountById(int id) {
        return accountDao.getAccountById(id);
    }
}
