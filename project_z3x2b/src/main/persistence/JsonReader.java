package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads expense history from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads expense history from file and returns it;
    // throws IOException if an error occurs reading data from file
    public History read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHistory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses history from JSON object and returns it
    private History parseHistory(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        History h = new History(name);
        addItems(h, jsonObject);
        return h;
    }

    // MODIFIES: h
    // EFFECTS: parses expenses from JSON object and adds them to history
    private void addItems(History h, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(h, nextItem);
        }
    }

    // MODIFIES: h
    // EFFECTS: parses expense from JSON object and adds it to history
    private void addItem(History h, JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        ExpenseDate date = addDate(jsonObject.getJSONObject("date"));
        boolean isExpense = jsonObject.getBoolean("isExpense");
        if (isExpense) {
            String type = jsonObject.getString("type");
            Item expense = new Expense(amount, type, date);
            h.add(expense);
        } else {
            Item income = new Income(amount, date);
            h.add(income);
        }
    }

    // MODIFIES: h
    // EFFECTS: parses date from JSON object and adds it to expense
    private ExpenseDate addDate(JSONObject jsonObject) {
        String year = jsonObject.getString("year");
        String month = jsonObject.getString("month");
        String day = jsonObject.getString("day");
        String time = jsonObject.getString("time");
        ExpenseDate expenseDate = new ExpenseDate();
        expenseDate.setYear(year);
        expenseDate.setMonth(month);
        expenseDate.setDay(day);
        expenseDate.setTime(time);
        return expenseDate;
    }
}