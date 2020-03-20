package be.pxl.student;

import be.pxl.student.entity.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Scanner;

public class BudgetPlannerFindPayents {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory= null;
        EntityManager entityManager = null;
        try {
            Scanner input = new Scanner(System.in);
            entityManagerFactory = Persistence.createEntityManagerFactory("musicdb_pu");
            entityManager = entityManagerFactory.createEntityManager();
            System.out.println("Geef een naam: ");
            String name = input.nextLine();
            TypedQuery<Account> findByName = entityManager.createNamedQuery("findByName", Account.class);
            // TODO finish this
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }
}
