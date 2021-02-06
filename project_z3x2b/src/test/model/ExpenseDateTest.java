package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseDateTest {
    private ExpenseDate testED;
    private Date testDate;

    @BeforeEach
    void runBefore(){
        testED = new ExpenseDate();
        testDate = new Date();
    }

    @Test
    void testConstructor(){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        assertEquals(sdf1.format(testDate),testED.getYear());
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        assertEquals(sdf2.format(testDate),testED.getMonth());
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
        assertEquals(sdf3.format(testDate),testED.getDay());
        SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm:ss");
        assertEquals(sdf4.format(testDate),testED.getTime());
    }

    @Test
    void testFormatDate(){
        SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy");
        assertEquals(sdf5.format(testDate),ExpenseDate.formatDate(testDate,"yyyy"));
    }

    @Test
    void testSetYearDayTime(){
        testED.setTime("11:55:55");
        testED.setDay("18");
        testED.setYear("2019");
        assertEquals("11:55:55",testED.getTime());
        assertEquals("18",testED.getDay());
        assertEquals("2019",testED.getYear());
    }
}
