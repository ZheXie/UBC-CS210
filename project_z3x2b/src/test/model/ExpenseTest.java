package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {
    private Expense testExpense;

    @BeforeEach
    void runBefore(){
        testExpense = new Expense(78,"Food");
    }

    @Test
    void testConstructor(){
        assertEquals(78,testExpense.getAmount());
        assertEquals("Food",testExpense.getType());
    }

    @Test
    void testSetExpenseDate(){
        testExpense.setDate("1926","08","17","12:00:00");
        assertEquals("1926",testExpense.getDate().getYear());
        assertEquals("08",testExpense.getDate().getMonth());
        assertEquals("17",testExpense.getDate().getDay());
        assertEquals("12:00:00",testExpense.getDate().getTime());
    }

    @Test
    void testToString(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        String dateString = sdf.format(date);
        String expected = "Spent 78.0$ on Food at " + dateString;
        assertEquals(expected,testExpense.toString());
    }
}