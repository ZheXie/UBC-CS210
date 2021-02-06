package model;

import model.exceptions.EmptyListException;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

import org.json.*;

// A expense history that record all the expenses made by user.
public class History implements Writable {
    private String name;
    private List<Item> history;    // A list that store all the items.

    // getters and setters
    public List<Item> getItems() {
        return history;
    }

    public String getName() {
        return name;
    }

    //EFFECTS: make a new list to store expenses.
    public History(String name) {
        this.name = name;
        this.history = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: Add an expense to the list in expense history.
    public void add(Item item) {
        history.add(item);
    }

    //EFFECTS: Flit the list by selecting out the expenses
    //         with certain type if the list is not empty,
    //         and return them in a new list. Otherwise throw an EmptyListException.
    public List<Item> flitByType(String type, List<Item> expenses) throws EmptyListException {
        if (expenses.size() == 0) {
            throw new EmptyListException();
        }
        List<Item> expenseOfThisType = new ArrayList<>();
        for (Item i : expenses) {
            Expense e = (Expense) i;
            if (e.getType().equals(type)) {
                expenseOfThisType.add(i);
            }
        }
        return expenseOfThisType;
    }

    //EFFECTS: Flit the list by selecting out the expenses
    //         with certain month if the list is not empty, and return them in a new list.
    //         Otherwise throw an EmptyListException.
    public List<Item> flitByMonth(String month, List<Item> expenses) throws EmptyListException {
        if (expenses.size() == 0) {
            throw new EmptyListException();
        }
        List<Item> expenseOfThisMonth = new ArrayList<>();
        for (Item i : expenses) {
            if (i.getDate().getMonth().equals(month)) {
                expenseOfThisMonth.add(i);
            }
        }
        return expenseOfThisMonth;
    }

    //EFFECTS: Extract all the expense from a history
    //         if the history is not empty and return them in a list.
    //         Otherwise throw an EmptyListException.
    public List<Item> extractAllExpense() throws EmptyListException {
        if (history.size() == 0) {
            throw new EmptyListException();
        }
        List<Item> expenses = new ArrayList<>();
        for (Item i : history) {
            if (i.getIsExpense()) {
                expenses.add(i);
            }
        }
        return expenses;
    }

    //EFFECTS: Extract all the incomes from a history
    //         if the history is not empty, and return them in a list.
    //         Otherwise throw an EmptyListException.
    public List<Item> extractAllIncome() throws EmptyListException {
        if (history.size() == 0) {
            throw new EmptyListException();
        }
        List<Item> incomes = new ArrayList<>();
        for (Item i : history) {
            if (!i.getIsExpense()) {
                incomes.add(i);
            }
        }
        return incomes;
    }

    //EFFECTS: Calculate the sum of the expenses by adding
    //         their amount together if the history is not empty.
    //         Otherwise throw an EmptyListException.
    public double sumItems(List<Item> items) throws EmptyListException {
        if (items.size() == 0) {
            throw new EmptyListException();
        }
        double sum = 0.0;
        for (Item i : items) {
            sum += i.getAmount();
        }
        return sum;
    }

    //EFFECTS: Calculate the net income of this month if the history is not empty.
    //         Substracting the sum of incomes by the sum of expenses.
    //         Otherwise throw an EmptyListException.
    public double netIncome() throws EmptyListException {
        List<Item> expenses = extractAllExpense();
        List<Item> incomes = extractAllIncome();
        double netIncome = sumItems(incomes) - sumItems(expenses);
        return netIncome;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("items", itemsToJson());
        return json;
    }

    // EFFECTS: returns things in this expense history as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : history) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }
}

