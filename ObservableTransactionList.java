package com.example.final_inf_202;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Observable;
import java.util.Observer;

class Transaction_1 extends Transaction {
    private String description;
    private String phone;
    private String  amount;
    private String date;
    private String id;

    public Transaction_1(String phone, String description, String  amount, String date,String id) {
        this.phone=phone;
        this.amount=amount;
        this.description = description;
        this.date=date;
        this.id=id;
    }

    public String getDescription() {
        return description;
    }
    public String getAmount(){
        return amount;
    }
    public String getPhone() {
        return phone;
    }
    public String getDate(){

        return date;
    }
    public String getId(){
        return  id;
    }
    public String  toString() {
            return "recipient id:" +  getPhone() + "// phone: " + getId() +  "// transfer type:" + getDescription() + "// amount:" + getAmount() + "// date:" + getDate();
    }
}

 public class ObservableTransactionList extends Observable {
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public void addTransaction(Transaction_1 transaction) {
        //transactions.add(transaction);
         setChanged();
        notifyObservers(transaction);

    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }
}

class DatabaseObserver implements Observer {
    private ObservableTransactionList transactionList;

    public DatabaseObserver(ObservableTransactionList transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ObservableTransactionList && arg instanceof Transaction_1) {
            Transaction_1 transaction = (Transaction_1) arg;
            saveTransactionToDatabase(transaction);
        }
    }

    private void saveTransactionToDatabase(Transaction_1 transaction) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project","postgres","Dastan1506");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transfer (id_r,id_s,phone_r,description,amount,timee) VALUES (?,?,?,?,?,?)")) {

            // Check if the transaction already exists in the database
            if (!isTransactionExists(transaction)) {
                preparedStatement.setString(1, transaction.getPhone());
                preparedStatement.setString(2, String.valueOf(LoggedUser.getId()));
                preparedStatement.setString(3,transaction.getId());
                preparedStatement.setString(4, transaction.getDescription());
                preparedStatement.setString(5, transaction.getAmount());
                preparedStatement.setString(6, String.valueOf(Date.valueOf(transaction.getDate())));
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isTransactionExists(Transaction_1 transaction) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project","postgres","Dastan1506");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM transfer WHERE id_r=? AND id_s =? AND phone_r = ? AND description=? AND amount=? AND timee=?")) {

            preparedStatement.setString(1, transaction.getPhone());
            preparedStatement.setString(2, String.valueOf(LoggedUser.getId()));
            preparedStatement.setString(3,transaction.getId());
            preparedStatement.setString(4, transaction.getDescription());
            preparedStatement.setString(5, transaction.getAmount());
            preparedStatement.setString(6, String.valueOf(Date.valueOf(transaction.getDate())));

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            return count > 0;
        }
    }
}


