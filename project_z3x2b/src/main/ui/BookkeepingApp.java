//package ui;
//
//import model.Expense;
//import model.History;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//import java.text.NumberFormat;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//// Bookkeeping application
//public class BookkeepingApp {
//    private static final String JSON_STORE = "./data/history.json";
//    private double limit;
//    private double income;
//    private History history;
//    private Scanner input;
//    private List<String> types;
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;
//
//    //EFFECTS: runs the bookkeeping application and initialize all the fields
//    public BookkeepingApp() throws FileNotFoundException {
//        history = new History("Will's expense history");
//        input = new Scanner(System.in);
//        System.out.println("\nPlease set your income of this month.");
//        income = input.nextDouble();
//        System.out.println("\nPlease set your expense limit of this month.");
//        limit = input.nextDouble();
//        types = new ArrayList<>();
//        types.add("Food");
//        types.add("Shopping");
//        types.add("Commute");
//        types.add("Education");
//        types.add("Entertainment");
//        types.add("Relationship");
//        types.add("Housing");
//        types.add("MedicalCare");
//        types.add("Else");
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//        runApp();
//    }
//
//    //MODIFIES: this
//    //EFFECTS: process user input
//    private void runApp() {
//        boolean keepGoing = true;
//        String command = null;
//
//        while (keepGoing) {
//            displayMenu();
//            command = input.next();
//
//            if (command.equals("q")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//
//        System.out.println("\nGoodbye!");
//    }
//
//    //EFFECTS: displays menu of options to user
//    private void displayMenu() {
//        System.out.println("\nSelect From the following options by typing them on the screen.");
//        System.out.println("\tr -> Reset income");
//        System.out.println("\tc -> Change expense limit");
//        System.out.println("\ta -> Add expense");
//        System.out.println("\tv -> View expense history");
//        System.out.println("\ts -> save expense history to file");
//        System.out.println("\tl -> load expense history from file");
//        System.out.println("\tq -> Quit");
//    }
//
//    //MODIFIES: this
//    //EFFECTS: process user command
//    private void processCommand(String command) {
//        if (command.equals("a")) {
//            addExpense();
//        } else if (command.equals("r")) {
//            resetIncome();
//        } else if (command.equals("v")) {
//            viewHistory();
//        } else if (command.equals("c")) {
//            changeLimit();
//        } else if (command.equals("s")) {
//            saveExpenseHistory();
//        } else if (command.equals("l")) {
//            loadExpenseHistory();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }
//
//    // getters and setters
//    public double getLimit() {
//        return limit;
//    }
//
//    public void setLimit(double limit) {
//        this.limit = limit;
//    }
//
//    //MODIFIES: this
//    //EFFECTS: reset the expend limit of this moth
//    private void changeLimit() {
//        double limit = 0.0;       // force entry into loop
//        while (limit <= 0.0) {
//            System.out.print("Please enter your monthly expense limit: $");
//            limit = input.nextDouble();
//        }
//        this.limit = limit;
//        System.out.println("Expense limit has been reset to " + limit);
//    }
//
//    //MODIFIES: this
//    //EFFECTS: add an expense to the expense history
//    private void addExpense() {
//        double amount = 0.0;       // force entry into loop
//        while (amount <= 0.0) {
//            System.out.print("Please enter the amount of expense: $");
//            amount = input.nextDouble();
//        }
//
//        String type = selectType();
//
//        Expense expense = new Expense(amount, type);
//
//        history.add(expense);
//        boolean exceed = history.isApproachLimit(limit);
//        if (exceed) {
//            System.out.println("Your monthly expense is approaching expense limit!!!!!");
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: reset the income of this month.
//    private void resetIncome() {
//        double income = 0.0;       // force entry into loop
//        while (income <= 0.0) {
//            System.out.print("Please enter your monthly income: $");
//            income = input.nextDouble();
//        }
//        this.income = income;
//        System.out.println("Income has been reset to " + income);
//    }
//
//    // EFFECTS: saves the expense history to file
//    private void saveExpenseHistory() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(history);
//            jsonWriter.close();
//            System.out.println("Saved " + history.getName() + " to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads expense history from file
//    private void loadExpenseHistory() {
//        try {
//            history = jsonReader.read();
//            System.out.println("Loaded " + history.getName() + " from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: print expense history on the screen
//    //         and process user input for next step
//    private void viewHistory() {
//        printExpenses(history.getExpenseHistory());
//
//        boolean keepGoing = true;
//        String command2 = null;
//        while (keepGoing) {
//            displayMenu2();
//            command2 = input.next();
//
//            if (command2.equals("b")) {
//                keepGoing = false;
//            } else {
//                processCommand2(command2);
//            }
//        }
//    }
//
//    //EFFECTS: displays menu of options to user
//    private void displayMenu2() {
//        System.out.println("\nWhat do you want to do?");
//        System.out.println("\tc -> Calculate net income");
//        System.out.println("\tg -> Generate a report");
//        System.out.println("\tt -> Check expenses on a certain type");
//        System.out.println("\tb -> Back");
//    }
//
//    //MODIFIES: this
//    //EFFECTS: process user command
//    private void processCommand2(String command2) {
//        if (command2.equals("c")) {
//            calculateNetIncome();
//        } else if (command2.equals("g")) {
//            generateReport();
//        } else if (command2.equals("t")) {
//            checkCertainType();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }
//
//    //MODIFIES: this
//    //EFFECTS: calculate the net income of this month
//    private void calculateNetIncome() {
//        SimpleDateFormat sdf = new SimpleDateFormat("MM");
//        Date date = new Date();
//        String month = sdf.format(date);
//        double netIncome = income - history.sumExpense(history.flitByMonth(month, history.getExpenseHistory()));
//        System.out.println("Your net income of this month is " + netIncome);
//    }
//
//    //MODIFIES: this
//    //EFFECTS: generate a report of expense in certain month.
//    //         the report contains the percentage takes up by
//    //         each type of expense in this month.
//    private void generateReport() {
//        String month = chooseMonth();
//        NumberFormat nt = NumberFormat.getPercentInstance();
//        nt.setMinimumFractionDigits(2);
//
//        for (String type : types) {
//            double typePercentage = history.sumExpense(history.flitByType(type,
//                    history.flitByMonth(month, history.getExpenseHistory())))
//                    / history.sumExpense(history.flitByMonth(month, history.getExpenseHistory()));
//            System.out.println(type + " takes up to " + nt.format(typePercentage) + " in month " + month);
//        }
//    }
//
//    //EFFECTS: print the information of expenses that
//    //         belong to a certain type onto the screen
//    private void checkCertainType() {
//        String month = chooseMonth();
//        String type = chooseType();
//
//        List<Expense> expenseOfThisType = history.flitByType(
//                type, history.flitByMonth(month, history.getExpenseHistory()));
//        printExpenses(expenseOfThisType);
//    }
//
//    //EFFECTS: choose the month expected by user.
//    private String chooseMonth() {
//        String month = "";       // force entry into loop
//        while (!((month.equals("01")) || (month.equals("02")) || (month.equals("03")) || (month.equals("04"))
//                || (month.equals("05")) || (month.equals("06")) || (month.equals("07")) || (month.equals("08"))
//                || (month.equals("09")) || (month.equals("10")) || (month.equals("11")) || (month.equals("12")))) {
//            System.out.println("Please enter the month you want to check.");
//            month = input.next();
//        }
//        return month;
//    }
//
//    //EFFECTS: choose the type expected by user.
//    private String chooseType() {
//        String type = "";       // force entry into loop
//        while (!validType(type)) {
//            System.out.println("\nPlease enter the type you want to check.");
//            System.out.println("Choose from the following types: Food, Shopping, Commute, Education, "
//                    + "Entertainment, Relationship, Housing, MedicalCare, Else.");
//            type = input.next();
//        }
//        return type;
//    }
//
//    //EFFECTS: receive the type expected by user.
//    private String selectType() {
//        String type = "";       // force entry into loop
//        while (!validType(type)) {
//            System.out.println("\nPlease enter the type of expense.");
//            System.out.println("Choose from the following types: Food, Shopping, Commute, Education, "
//                    + "Entertainment, Relationship, Housing, MedicalCare, Else.");
//            type = input.next();
//        }
//        return type;
//    }
//
//    //EFFECTS: print a expense's information to the screen
//    private void printExpenses(List<Expense> expenses) {
//        for (Expense e : expenses) {
//            System.out.print("Spent " + e.getAmount() + "$ on ");
//            System.out.print(e.getExpenseType() + " at ");
//            System.out.print(e.getExpenseDate().getYear() + "-");
//            System.out.print(e.getExpenseDate().getMonth() + "-");
//            System.out.print(e.getExpenseDate().getDay() + ", ");
//            System.out.println(e.getExpenseDate().getTime());
//        }
//    }
//
//    //EFFECTS: return true if the type entered is one of the valid types
//    //         otherwise return false.
//    private boolean validType(String type) {
//        for (String p: types) {
//            if (type.equals(p)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
