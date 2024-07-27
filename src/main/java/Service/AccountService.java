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

    /**
     * This method allows to add a user to the database when there is a legal username(not blank, or not previously used),
     * and legal password(4 characters or more).
     * @param account contains the information about the user account to add.
     * @return the newly created user account if successful, otherwise null
     */
    public Account addUserAccount(Account account) {
        if (account.getUsername().length() > 0 &&
            account.getPassword().length() >= 4 &&
            accountDao.getAccountByUsername(account.getUsername()) == null) {
                return accountDao.insertUser(account);
        }
        return null;
    }

    /**
     * This method is used to verify credentials of a user to allow the login process
     * @param account contains the user supplied information/credentials
     * @return Account if the credentials are a match, otherwise null
     */
    public Account processUserLogin(Account account) {
        Account retrievedAccount = accountDao.getAccountByUsername(account.getUsername());
        
        if (retrievedAccount != null &&
            retrievedAccount.getPassword().equals(account.getPassword())) {
                return retrievedAccount;
        }
        return null;
    }

    /**
     * This method allow us to fetch user account by id.
     * @param id is the user supplied id.
     * @return Account if the user id exist, otherwise null.
     */
    public Account getAccountById(int id) {
        return accountDao.getAccountById(id);
    }
}
