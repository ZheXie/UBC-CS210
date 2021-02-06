package ui;

import model.*;
import model.exceptions.EmptyListException;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.audio.AudioPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;

// Bookkeeping application with graphical user interface.
public class BookkeepingAppWithGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/history.json";
    private History history;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //declare each fragment of the APP frame.
    JFrame appFrame = new JFrame("Bookkeeping App");

    JLabel saveLabel = new JLabel("Click save to save history.");
    JButton saveButton = new JButton("Save");
    JLabel loadLabel = new JLabel("Click load to load history.");
    JButton loadButton = new JButton("Load");
    JLabel addLabel = new JLabel("Click add to add a new item to history.");
    JButton addButton = new JButton("Add");
    JLabel sumLabel = new JLabel("Click sum to calculate the money spent in this month.");
    JButton sumButton = new JButton("Sum");
    JLabel netLabel = new JLabel("Click net income to calculate the net income of this month.");
    JButton netButton = new JButton("Net Income");
    JLabel checkLabel = new JLabel("Check expenses spent on certain type of item.");
    JComboBox<String> typeSelect2 = new JComboBox<>();
    JButton checkButton = new JButton("Check");
    JTextArea historyWithTypeText = new JTextArea(40, 50);
    JTextArea historyText = new JTextArea(50, 50);

    JDialog enterExpense = new JDialog();
    JCheckBox expenseItem = new JCheckBox("Expense",true);
    JCheckBox incomeItem = new JCheckBox("Income",false);
    JLabel amount = new JLabel("Expense amount:");
    JTextField expenseAmount = new JTextField(30);
    JLabel cad = new JLabel("CAD");
    JLabel type = new JLabel("Expense type:");
    JComboBox<String> typeSelect = new JComboBox<>();
    JButton confirmButton = new JButton("Confirm");

    //EFFECTS: initialize all the fields, create GUI and make it visible.
    public void initApp() {
        history = new History("Will's expense history");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initTypeSelect();

        initAppFrame();

        initEnterExpense();

        appFrame.pack();
        appFrame.setVisible(true);
    }

    //EFFECTS: assembling the fragments of app's frame.
    public void initAppFrame() {
        Box left = initLeftSide();

        Box whole = Box.createHorizontalBox();
        whole.add(left);
        whole.add(Box.createHorizontalStrut(20));
        whole.add(historyText);

        appFrame.add(whole);
        appFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //EFFECTS: returns a box that represents left side of the app.
    public Box initLeftSide() {
        Box left = Box.createVerticalBox();
        left.add(addLabel);
        left.add(addButton);
        addButton.addActionListener(this);
        left.add(loadLabel);
        left.add(loadButton);
        loadButton.addActionListener(this);
        left.add(saveLabel);
        left.add(saveButton);
        saveButton.addActionListener(this);
        left.add(sumLabel);
        left.add(sumButton);
        sumButton.addActionListener(this);
        left.add(netLabel);
        left.add(netButton);
        netButton.addActionListener(this);
        left.add(checkLabel);
        left.add(initCheck());
        return left;
    }

    //EFFECTS: returns a box that represents the check by type button.
    public Box initCheck() {
        initTypeSelect2();
        Box check = Box.createVerticalBox();
        Box checkUpper = Box.createHorizontalBox();
        checkUpper.add(checkButton);
        checkButton.addActionListener(this);
        checkUpper.add(typeSelect2);
        check.add(checkUpper);
        check.add(historyWithTypeText);
        return check;
    }

    //EFFECTS: assembling the enter expense dialog box
    public void initEnterExpense() {
        enterExpense.setBounds(600, 600, 70, 20);
        Box expense = Box.createHorizontalBox();
        expense.add(expenseItem);
        expense.add(incomeItem);
        expense.add(amount);
        expense.add(expenseAmount);
        expense.add(cad);
        expense.add(Box.createHorizontalStrut(10));
        expense.add(type);
        expense.add(typeSelect);
        expense.add(confirmButton);
        confirmButton.addActionListener(this);
        enterExpense.add(expense);
        enterExpense.pack();
    }

    //EFFECTS: assembling the selecting combo box in enter expense dialog
    public void initTypeSelect() {
        typeSelect.addItem("Food");
        typeSelect.addItem("Shopping");
        typeSelect.addItem("Commute");
        typeSelect.addItem("Education");
        typeSelect.addItem("Entertainment");
        typeSelect.addItem("Relationship");
        typeSelect.addItem("Housing");
        typeSelect.addItem("MedicalCare");
        typeSelect.addItem("Else");
    }

    //EFFECTS: assembling the selecting combo box in App's frame.
    public void initTypeSelect2() {
        typeSelect2.addItem("Food");
        typeSelect2.addItem("Shopping");
        typeSelect2.addItem("Commute");
        typeSelect2.addItem("Education");
        typeSelect2.addItem("Entertainment");
        typeSelect2.addItem("Relationship");
        typeSelect2.addItem("Housing");
        typeSelect2.addItem("MedicalCare");
        typeSelect2.addItem("Else");
    }

    //EFFECTS: Handle the events when clicking the buttons.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            playMusic();
            enterExpense.setVisible(true);
        } else if (e.getSource() == confirmButton) {
            playMusic();
            confirm();
        } else if (e.getSource() == saveButton) {
            playMusic();
            saveFile();
        } else if (e.getSource() == loadButton) {
            playMusic();
            loadFile();
        } else if (e.getSource() == sumButton) {
            playMusic();
            sumExpense();
        } else if (e.getSource() == netButton) {
            playMusic();
            calculateNetIncome();
        } else if (e.getSource() == checkButton) {
            playMusic();
            checkByType();
        }
    }

    //EFFECTS: Handle add expense
    public void confirm() {
        String amount = expenseAmount.getText();
        if (expenseItem.isSelected() || !(incomeItem.isSelected())) {
            String selectedType = (String) typeSelect.getSelectedItem();
            Item expense = new Expense(Integer.parseInt(amount), selectedType);
            history.add(expense);
            historyText.append(expense.toString() + "\n");
        } else if (!(expenseItem.isSelected()) || incomeItem.isSelected()) {
            Item income = new Income(Integer.parseInt(amount));
            history.add(income);
            historyText.append(income.toString() + "\n");
        }
    }

    //EFFECTS: handle sum expense
    public void sumExpense() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Date date = new Date();
        String month = sdf.format(date);
        double sum = 0;
        try {
            sum = history.sumItems(history.flitByMonth(month, history.extractAllExpense()));
            historyText.append("The money you spent up to now is " + sum + "$" + "\n");
        } catch (EmptyListException e) {
            historyText.append("Please add an item to history or load a file." + "\n");
        }
    }

    //EFFECTS: handle check by type event
    public void checkByType() {
        String selectedType = (String) typeSelect2.getSelectedItem();
        List<Item> expenseOfThisType = null;
        try {
            expenseOfThisType = history.flitByType(selectedType, history.extractAllExpense());
            historyWithTypeText.setText(getItemString(expenseOfThisType));
        } catch (EmptyListException e) {
            historyWithTypeText.setText("Please add an item to history or load a file." + "\n");
        }
    }

    //EFFECTS: handle save event
    public void saveFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(history);
            jsonWriter.close();
            historyText.append("Saved " + history.getName() + " to " + JSON_STORE + "\n");
        } catch (FileNotFoundException exception) {
            historyText.append("Unable to write to file: " + JSON_STORE + "\n");
        }
    }

    //EFFECTS: handle load event
    public void loadFile() {
        try {
            history = jsonReader.read();
            historyText.setText(getItemString(history.getItems()));
            historyText.append("Loaded " + history.getName() + " from " + JSON_STORE + "\n");
            historyWithTypeText.setText("");
        } catch (IOException exception) {
            historyText.append("Unable to read from file: " + JSON_STORE + "\n");
        }
    }

    //EFFECTS: calculate the net income of this month.
    public void calculateNetIncome() {
        double sum = 0;
        try {
            sum = history.netIncome();
            historyText.append("The net income of this month is " + sum + "$" + "\n");
        } catch (EmptyListException e) {
            historyText.append("Please add an item to history or load a file." + "\n");
        }
    }

    //EFFECTS: print an item's information to the screen
    private String getItemString(List<Item> items) {
        String itemsString = "";
        for (Item i : items) {
            itemsString = itemsString + i.toString() + "\n";
        }
        return itemsString;
    }

    //EFFECTS: handle play music event
    public void playMusic() {
        try {
            FileInputStream audio = new FileInputStream("./data/audio.wav");
            AudioPlayer.player.start(audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // the place where java access the program.
    public static void main(String[] args) {
        new BookkeepingAppWithGUI().initApp();
    }
}