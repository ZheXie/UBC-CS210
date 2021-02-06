package persistence;

import model.ExpenseDate;
import model.Expense;
import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkExpense(Double amount, String type, ExpenseDate date, Item item) {
        Expense expense = (Expense) item;
        assertEquals(amount, item.getAmount());
        assertEquals(type, expense.getType());
        assertEquals(date.getYear(), item.getDate().getYear());
        assertEquals(date.getMonth(), item.getDate().getMonth());
        assertEquals(date.getDay(), item.getDate().getDay());
        assertEquals(date.getTime(), item.getDate().getTime());
    }

    protected void checkIncome(Double amount, ExpenseDate date, Item item) {
        assertEquals(amount, item.getAmount());
        assertEquals(date.getYear(), item.getDate().getYear());
        assertEquals(date.getMonth(), item.getDate().getMonth());
        assertEquals(date.getDay(), item.getDate().getDay());
        assertEquals(date.getTime(), item.getDate().getTime());
    }
}

