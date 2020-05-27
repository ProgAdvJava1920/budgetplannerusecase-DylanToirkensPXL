package be.pxl.student.dao.impl;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private static final Logger LOGGER = LogManager.getLogger(AccountDao.class);

    private EntityManager entityManager;

    public AccountDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Account findAccountByName(String name) {
        // Een named query zit altijd bij je entity. In dit geval bij je entity Account
        TypedQuery<Account> query = entityManager.createNamedQuery("findByName", Account.class);
        LOGGER.info("query with name [" + name + "]");
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Account findAccountByIBAN(String iban) {
        TypedQuery<Account> query = entityManager.createNamedQuery("findByIban", Account.class);
        LOGGER.info("query with iban [" + iban + "]");
        query.setParameter("iban", iban);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Account updateAccount(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        account = entityManager.merge(account);
        transaction.commit();
        return account;
    }

    @Override
    public Account createAccount(Account account) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(account);
        transaction.commit();
        return account;
    }

    @Override
    public List<Account> findAllAccounts() {
        // TODO test this
        TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a", Account.class);
        return query.getResultList();
    }
}
