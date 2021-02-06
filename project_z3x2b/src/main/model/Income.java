package model;

import org.json.JSONObject;

// Represents an income item having an amount(in dollars), a type, and a date
public class Income extends Item {

    //REQUIRES: amount has a non-zero amount.
    //EFFECTS: the income's amount is set to amount,
    //         and the income's type is set to type.
    public Income(double amount) {
        super(amount);
        super.isExpense = false;
    }

    //REQUIRES: amount has a non-zero amount.
    //EFFECTS: the income's amount is set to amount,
    //         and the income's type is set to type,
    //         and the income's date is set to the time when this new object is created.
    public Income(double amount, ExpenseDate date) {
        super(amount, date);
        super.isExpense = false;
    }

    //EFFECTS: convert an expense object to a string object.
    @Override
    public String toString() {
        String expense = "Earned " + amount + "$ at "
                + date.getYear() + "-" + date.getMonth() + "-"
                + date.getDay() + ", " + date.getTime();
        return expense;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("isExpense", isExpense);
        json.put("amount", amount);
        json.put("date", date.toJson());
        return json;
    }
}
