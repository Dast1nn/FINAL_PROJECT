package com.example.final_inf_202;
import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class Profile {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label balance;

    @FXML
    private Button change_password;

    @FXML
    private Label email;

    @FXML
    private Button go_back;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    void change_password_button(ActionEvent event) throws IOException {
        change_password.getScene().getWindow().hide();
        Stage signup = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Change_password.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 738, 476);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);
    }
    @FXML
    void go_back_button(ActionEvent event) throws IOException {
        go_back.getScene().getWindow().hide();
        Stage signup = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 738, 476);
        signup.setScene(scene);
        signup.show();
        signup.setResizable(false);
    }

    @FXML
    void initialize() {
        UserProfile userProfile = fetchUserProfile();
        if (userProfile != null) {
            name.setText(((ProfileLeaf) userProfile.getComponent("Name")).getValue());
            email.setText(((ProfileLeaf) userProfile.getComponent("Email")).getValue());
            phone.setText(((ProfileLeaf) userProfile.getComponent("Phone")).getValue());
            balance.setText(((ProfileLeaf) userProfile.getComponent("Balance")).getValue());
        }
    }

    private UserProfile fetchUserProfile() {
        UserProfile userProfile = new UserProfile();
        DatabaseSingleton databaseSingleton = DatabaseSingleton.getInstance();

        try (Connection connection = databaseSingleton.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT name,email,phone,balance FROM regis WHERE id='" + LoggedUser.getId() + "'")) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String balance = resultSet.getString("balance");

                userProfile.addComponent(new ProfileLeaf("Name", name));
                userProfile.addComponent(new ProfileLeaf("Email", email));
                userProfile.addComponent(new ProfileLeaf("Phone", phone));
                userProfile.addComponent(new ProfileLeaf("Balance", balance));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userProfile;
    }

}
