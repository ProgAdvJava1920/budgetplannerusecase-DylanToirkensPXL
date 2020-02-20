package be.pxl.student.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
            while ((line = reader.readLine()) != null) {
                //TODO parse lines
                System.out.println(line);

            }
        } catch (IOException e) {
            LOGGER.fatal("An erroroccured while reading file: {}", path);
        }
//        String[] lines = Files.lines(path)
//                .toArray(String[]::new);

//        for (int i = 1; i < lines.length; i++) {
//            String[] lineInfo = lines[i].split(",");
//            String accountName = lineInfo[accountNameIndex];
//            String accountIBAN = lineInfo[accountIBANIndex];
//        }
    }
}
