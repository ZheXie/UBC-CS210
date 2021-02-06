package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an item having an amount(in dollars), a type, and a date
public abstract class Item implements Writable {
    protected Boolean isExpense;               // whether the item is a expense.
    protected final double amount;                 // the item's amount.
    protected final ExpenseDate date;              // the item's date.

    //REQUIRES: amount has a non-zero amount.
    //EFFECTS: the item's amount is set to amount,
    //         and the item's type is set to type.
    public Item(double amount) {
        this.amount = amount;
        this.date = new ExpenseDate();
    }

    //REQUIRES: amount has a non-zero amount.
    //EFFECTS: the item's amount is set to amount,
    //         and the item's type is set to type,
    //         and the item's date is set to the time when this new object is created.
    public Item(double amount, ExpenseDate date) {
        this.amount = amount;
        this.date = date;
    }

    //getters and setters
    public ExpenseDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public Boolean getIsExpense() {
        return isExpense;
    }

    public void setDate(String year, String month, String day, String time) {
        this.date.setYear(year);
        this.date.setMonth(month);
        this.date.setDay(day);
        this.date.setTime(time);
    }

    @Override
    public abstract JSONObject toJson();
}
