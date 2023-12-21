package com.example.final_inf_202;

import javafx.scene.control.TextField;

public class LoggedUser {
    private static int id;
    private static  TextField phone;
    private static int amount;
    public static int  getId(){
        return id;
    }
    public static void setId(int id){
        LoggedUser.id= id;
    }
    public static TextField getPhone(){
        return phone;
    }
    public static void setPhone(TextField phone){
        LoggedUser.phone=phone;
    }
    public static int getAmount(){
        return amount;
    }
    public static void setAmount( int amount){
        LoggedUser.amount=amount;
    }

}
