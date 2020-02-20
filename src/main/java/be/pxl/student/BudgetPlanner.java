package be.pxl.student;

import be.pxl.student.util.BudgetPlannerImporter;

import java.io.IOException;
import java.nio.file.Paths;

public class BudgetPlanner {
    public static void main(String[] args) {
        new BudgetPlannerImporter().importCsv(Paths.get("src/main/resources/accsount_payments.csv"));
    }
}
