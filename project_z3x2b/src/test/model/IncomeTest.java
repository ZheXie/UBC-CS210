package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class IncomeTest {
    private Income testIncome;

    @BeforeEach
    void runBefore() {
        testIncome = new Income(88);
    }

    @Test
    void testConstructor(){
        assertEquals(88,testIncome.getAmount());
    }

    @Test
    void testSetExpenseDate(){
        testIncome.setDate("1926","08","17","12:00:00");
        assertEquals("1926",testIncome.getDate().getYear());
        assertEquals("08",testIncome.getDate().getMonth());
        assertEquals("17",testIncome.getDate().getDay());
        assertEquals("12:00:00",testIncome.getDate().getTime());
    }

    @Test
    void testToString(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        String dateString = sdf.format(date);
        String expected = "Earned 88.0$ at " + dateString;
        assertEquals(expected,testIncome.toString());
    }
}
