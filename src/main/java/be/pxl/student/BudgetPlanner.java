package be.pxl.student;

import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;

public class BudgetPlanner {
    private static final Logger LOGGER = LogManager.getLogger(BudgetPlanner.class);

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            LOGGER.info("start reading file");
            new BudgetPlannerImporter().importCsv(Paths.get("src/main/resources/account_payments.csv"));
            LOGGER.info("finished reading file");
        }
    }
}
