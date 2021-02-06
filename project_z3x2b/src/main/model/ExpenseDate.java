package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.SimpleDateFormat;
import java.util.Date;

// Represents the date when a expense is made, consists of year, month, day, and time.
public class ExpenseDate implements Writable {
    private Date dateNow = new Date();     // get time from system.
    private String year;                // the year when expense was made
    private String month;               // the month when expense was made
    private String day;                 // the day when expense was made
    private String time;                // the time when expense was made

    // getters and setters
    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    //MODIFIES: this
    //EFFECTS: extract the date in a certain format.
    public static String formatDate(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        result = sdf.format(date);
        return result;
    }

    //EFFECTS: assign the fragment of date (extracted from system)
    //         to the corresponding fields in ExpenseDate.
    public ExpenseDate() {
        this.year = formatDate(dateNow, "yyyy");
        this.month = formatDate(dateNow, "MM");
        this.day = formatDate(dateNow, "dd");
        this.time = formatDate(dateNow, "HH:mm:ss");
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("year", year);
        json.put("month", month);
        json.put("day", day);
        json.put("time", time);
        return json;
    }
}
