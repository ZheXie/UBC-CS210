
package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    private ExpenseDate date = new ExpenseDate();

    @Test
    void testWriterInvalidFile() {
        try {
            History h = new History("My expense history");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyHistory() {
        try {
            History h = new History("My expense history");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHistory.json");
            writer.open();
            writer.write(h);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHistory.json");
            h = reader.read();
            assertEquals("My expense history", h.getName());
            assertEquals(0, h.getItems().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralHistory() {
        try {
            History h = new History("My expense history");
            h.add(new Expense(400.0, "Food", date));
            h.add(new Expense(800.0, "Shopping", date));
            h.add(new Income(1200.0, date));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHistory.json");
            writer.open();
            writer.write(h);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHistory.json");
            h = reader.read();
            assertEquals("My expense history", h.getName());
            List<Item> items = h.getItems();
            assertEquals(3, items.size());
            checkExpense(400.0, "Food", date, items.get(0));
            checkExpense(800.0, "Shopping", date, items.get(1));
            checkIncome(1200.0,date,items.get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}