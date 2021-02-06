package persistence;

import model.Expense;
import model.ExpenseDate;
import model.History;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    private ExpenseDate date = new ExpenseDate();

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            History h = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyHistory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyHistory.json");
        try {
            History h = reader.read();
            assertEquals("My expense history", h.getName());
            assertEquals(0, h.getItems().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralHistory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHistory.json");
        try {
            date.setYear("2020");
            date.setMonth("03");
            date.setDay("12");
            date.setTime("12:00:00");
            History h = reader.read();
            assertEquals("My expense history", h.getName());
            List<Item> items = h.getItems();
            assertEquals(3, items.size());
            checkExpense(400.0, "Food", date, items.get(0));
            checkExpense(800.0, "Shopping", date, items.get(1));
            checkIncome(1200.0,date,items.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
