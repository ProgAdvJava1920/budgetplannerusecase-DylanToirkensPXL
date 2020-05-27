package be.pxl.student.service;

import be.pxl.student.AccountNotFoundException;
import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.impl.AccountDaoImpl;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.EntityManagerUtil;

import javax.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class AccountService {

    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDaoImpl(EntityManagerUtil.createEntityManager());
    }

    public List<Payment> findPaymentsByAccountName(String name) throws AccountNotFoundException {
        Account account = accountDao.findAccountByName(name);
        if (account == null) {
            throw new AccountNotFoundException("There is no account with the name [" + name + "]");
        }
        return account.getPayments();
    }

    // TODO LocalDate date param
    public void addPayment(String name, String counterAccountIBAN, float amount, String detail) throws AccountNotFoundException {
        Account account = accountDao.findAccountByName(name);
        if (account == null) {
            throw new AccountNotFoundException("There is no account with the name [" + name + "]");
        }
        Account counterAccount = accountDao.findAccountByIBAN(counterAccountIBAN);
        if (counterAccount == null) {
            counterAccount = new Account();
            counterAccount.setIBAN(counterAccountIBAN);
            counterAccount = accountDao.createAccount(counterAccount);
        }
        Payment payment = new Payment();
        payment.setCounterAccount(counterAccount);
        payment.setAmount(amount);
        payment.setCurrency("EUR");
        payment.setDate(LocalDateTime.now());
        payment.setDetail(detail);
        account.addPayment(payment);
        accountDao.updateAccount(account);
    }

    public List<Account> findAllAccounts() {
        return accountDao.findAllAccounts();
    }

    public void removeAccount(long accountId) {
        // TODO
    }
}
