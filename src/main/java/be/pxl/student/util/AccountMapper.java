package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.util.exception.InvalidPaymentException;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AccountMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

    public Account map(String validLine) throws InvalidPaymentException {
        String[] split = validLine.split(",");
        if (split.length != 7) {
            throw new InvalidPaymentException("Invalid number of fields in line.");
        }
        Account account = new Account();
        account.setName(split[0]);
        account.setIBAN(split[1]);
        // TODO move these 3 comments to payment mapper?
//        account.setPayments(new ArrayList<>());
//        Payment payment = new Payment(LocalDateTime.parse(split[3], FORMATTER), Float.parseFloat(split[4]), split[5], split[6]);
//        account.getPayments().add(payment);
        return account;
    }
}
