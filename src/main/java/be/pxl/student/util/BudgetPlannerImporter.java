package be.pxl.student.util;

import be.pxl.student.entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private static final Logger LOGGER = LogManager.getLogger(BudgetPlannerImporter.class);
    private PathMatcher csvMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv");

    public void importCsv(Path path) {
        if (!csvMatcher.matches(path)) {
            LOGGER.error("Invalid file: .csv expected. Provided: {}", path);
            return;
        }
        if (!Files.exists(path)) {
            LOGGER.error("File {} does not exist.", path);
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = null;
            reader.readLine(); // skip first line
            List<Account> accounts = new ArrayList<>();
            AccountMapper accountMapper = new AccountMapper();
            while ((line = reader.readLine()) != null) {
                accounts.add(accountMapper.map(line));
            }
            for (Account a : accounts) {
                System.out.println(a);
            }
        } catch (IOException e) {
            LOGGER.fatal("An erroroccured while reading file: {}", path);
        }
    }
}
