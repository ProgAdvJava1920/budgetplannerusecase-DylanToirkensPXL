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
    private AccountMapper accountMapper = new AccountMapper();

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
//            List<Account> accounts = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                try {
                    LOGGER.debug(accountMapper.map(line));
                } catch (InvalidPaymentException e) {
                    LOGGER.error("Error while mapping line: {}", e.getMessage());
                }
//                accounts.add(accountMapper.map(line));
            }
        } catch (IOException e) {
            LOGGER.fatal("An erroroccured while reading file: {}", path);
        }
    }
}
