package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountMapperTest {
    private String validLine = "Jos,BE69771770897312,BE17795215960626,Thu Feb 13 05:47:35 CET 2020,265.8,EUR,Ut ut necessitatibus itaque ullam.";
    private AccountMapper accountMapper = new AccountMapper();

    @Test
    public void aValidLineIsMappedToAnAccount() {
        Account result = accountMapper.map(validLine);
        assertNotNull(result);
        assertEquals("Jos", result.getName());
        assertEquals("BE69771770897312", result.getIBAN());
        assertEquals(1, result.getPayments().size());
        Payment resultPayment = result.getPayments().get(0);
        assertEquals(LocalDateTime.of(2020, 2, 13, 5, 47, 35), resultPayment.getDate());
        assertEquals("EUR", resultPayment.getCurrency());
        assertEquals(265.8, resultPayment.getAmount(), 0.01);
        assertEquals("Ut ut necessitatibus itaque ullam.", resultPayment.getDetail());
    }
}
