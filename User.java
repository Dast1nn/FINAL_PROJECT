package com.example.final_inf_202;
import java.math.RoundingMode;
public class User {
    private final int id;
    private final String username, password;
    private int currentBalance;

    public User(int id, String username, String password, int currentBalance){
        this.id = id;
        this.username = username;
        this.password = password;
        this.currentBalance = currentBalance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int  newBalance){
        currentBalance = newBalance;
    }
}
