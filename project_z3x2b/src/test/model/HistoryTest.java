package model;

import model.exceptions.EmptyListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {
    private History testHistory;

    @BeforeEach
    void runBefore(){
        testHistory = new History("My Expense History");
    }

    @Test
    void  testConstructor(){
        assertEquals(0,testHistory.getItems().size());
        assertEquals("My Expense History",testHistory.getName());
    }

    @Test
    void testAdd(){
        Expense expense = new Expense(78,"Food");
        Income income = new Income(88);
        assertEquals(0,testHistory.getItems().size());
        testHistory.add(expense);
        assertEquals(1,testHistory.getItems().size());
        testHistory.add(income);
        assertEquals(2,testHistory.getItems().size());
    }

    @Test
    void testFlitByType(){
        List<Item> expenses = new ArrayList<>();
        Expense expense1 = new Expense(1,"Food");
        Expense expense2 = new Expense(3,"Commute");
        Expense expense3 = new Expense(5,"Food");
        expenses.add(expense1);
        expenses.add(expense2);
        expenses.add(expense3);
        try {
            List<Item> expenseOfFood = testHistory.flitByType("Food", expenses);
            assertEquals(2, expenseOfFood.size());
            List<Item> expenseOfEducation = testHistory.flitByType("Commute", expenses);
            assertEquals(1, expenseOfEducation.size());
            List<Item> expenseOfShopping = testHistory.flitByType("Shopping", expenses);
            assertEquals(0, expenseOfShopping.size());
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    void testFlitByTypeWithEmptyList(){
        List<Item> expenses = new ArrayList<>();
        try {
            testHistory.flitByType("Food", expenses);
            fail();
        } catch (EmptyListException e) {
            // Expected
        }
    }

    @Test
    void testFlitByMonth(){
        List<Item> expenses = new ArrayList<>();
        Expense expense1 = new Expense(1,"Food");
        Expense expense2 = new Expense(3,"Food");
        Expense expense3 = new Expense(5,"Food");
        expense2.getDate().setMonth("9");
        expenses.add(expense1);
        expenses.add(expense2);
        expenses.add(expense3);
        try {
            List<Item> expenseOfFood = testHistory.flitByMonth("12", expenses);
            assertEquals(2, expenseOfFood.size());
            List<Item> expenseOfEducation = testHistory.flitByMonth("9", expenses);
            assertEquals(1, expenseOfEducation.size());
            List<Item> expenseOfShopping = testHistory.flitByMonth("8", expenses);
            assertEquals(0, expenseOfShopping.size());
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    void testFlitByMonthWithEmptyList(){
        List<Item> expenses = new ArrayList<>();
        try {
            testHistory.flitByMonth("11", expenses);
            fail();
        } catch (EmptyListException e) {
            // Expected
        }
    }

    @Test
    void testSumExpense() {
        List<Item> expenses = new ArrayList<>();
        Expense expense1 = new Expense(1,"Food");
        Expense expense2 = new Expense(3,"Food");
        Expense expense3 = new Expense(5,"Food");
        expenses.add(expense1);
        expenses.add(expense2);
        expenses.add(expense3);
        try {
            assertEquals(9, testHistory.sumItems(expenses));
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    void testSumExpenseWithEmptyHistory() {
        List<Item> expenses = new ArrayList<>();
        try {
            testHistory.sumItems(expenses);
            fail();
        } catch (EmptyListException e) {
            // Expected
        }
    }

    @Test
    void testExtractAllExpenseOrIncome() {
        Expense expense1 = new Expense(1,"Food");
        Expense expense2 = new Expense(3,"Food");
        Expense expense3 = new Expense(5,"Food");
        Income income1 = new Income(7);
        testHistory.add(expense1);
        testHistory.add(expense2);
        testHistory.add(expense3);
        testHistory.add(income1);
        try {
            assertEquals(3, testHistory.extractAllExpense().size());
            assertEquals(1, testHistory.extractAllIncome().size());
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    void testExtractAllExpenseWithEmptyHistory() {
        try{
            testHistory.extractAllExpense();
            fail();
        } catch (EmptyListException e) {
            // Expected
        }
    }

    @Test
    void testExtractAllIncomeWithEmptyHistory() {
        try{
            testHistory.extractAllIncome();
            fail();
        } catch (EmptyListException e) {
            // Expected
        }
    }

    @Test
    void testNetIncome() {
        Item expense1 = new Expense(1,"Food");
        Item expense2 = new Expense(3,"Food");
        Item expense3 = new Expense(5,"Food");
        Item income1 = new Income(7);
        testHistory.add(expense1);
        testHistory.add(expense2);
        testHistory.add(expense3);
        testHistory.add(income1);
        try {
            assertEquals(-2.0, testHistory.netIncome());
            Item income2 = new Income(5);
            testHistory.add(income2);
            assertEquals(3.0, testHistory.netIncome());
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    void testNetIncomeWithEmptyHistory() {
        try {
            testHistory.netIncome();
            fail();
        } catch (EmptyListException e) {
            // Expected
        }
    }
}
