package model;

import org.json.JSONObject;

// Represents an expense item having an amount(in dollars), a type, and a date
public class Expense extends Item {
    private final String type;

    // getter
    public String getType() {
        return type;
    }

    //REQUIRES: amount has a non-zero amount.
    //EFFECTS: the expense's amount is set to amount,
    //         and the expense's type is set to type.
    public Expense(double amount, String type) {
        super(amount);
        super.isExpense = true;
        this.type = type;
    }

    //REQUIRES: amount has a non-zero amount.
    //EFFECTS: the expense's amount is set to amount,
    //         and the expense's type is set to type,
    //         and the expense's date is set to the value input.
    public Expense(double amount, String type, ExpenseDate date) {
        super(amount, date);
        super.isExpense = true;
        this.type = type;
    }

    //EFFECTS: convert an expense object to a string object.
    @Override
    public String toString() {
        String expense = "Spent " + amount + "$ on " + type + " at "
                + date.getYear() + "-" + date.getMonth() + "-"
                + date.getDay() + ", " + date.getTime();
        return expense;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("isExpense", isExpense);
        json.put("amount", amount);
        json.put("type", type);
        json.put("date", date.toJson());
        return json;
    }
}
