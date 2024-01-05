package controllers;

import auth.AuthData;
import database.DatabaseQuery;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import shared.Methods;

public class AuthSceneController {

  @FXML
  private Button loginButton;

  @FXML
  private TextField passwordTf;

  @FXML
  private Button registerButton;

  @FXML
  private TextField usernameTf;

  @FXML
  void loginButtonClicked(ActionEvent event) {
    if (usernameTf.getText().isEmpty() || passwordTf.getText().isEmpty()) {
      Methods.displayAlert(
        "Missing Fields",
        "Please fill all the fields!",
        true
      );
      return;
    }

    String sql = "SELECT * FROM user WHERE username = ? and password = ?";

    try {
      ResultSet result = DatabaseQuery.executeSelectQuery(
        sql,
        usernameTf.getText(),
        passwordTf.getText()
      );

      if (result.next()) {
        AuthData.id = result.getInt(1);
        AuthData.username = result.getString(2);
        AuthData.isAdmin = result.getBoolean(4);

        Methods.displayAlert(
          "Successful Login",
          "You have logged in successfully!",
          false
        );

        Methods.navigateToMainScene(loginButton);
      } else {
        Methods.displayAlert(
          "Wrong Credentials",
          "Invalid username or password!",
          true
        );
      }
    } catch (Exception e) {
      System.out.println("loginButtonClicked() catch");
      e.printStackTrace();
    }
  }

  @FXML
  void registerButtonClicked(ActionEvent event) throws IOException {
    if (usernameTf.getText().isEmpty() || passwordTf.getText().isEmpty()) {
      Methods.displayAlert(
        "Missing Fields",
        "Please fill all the fields!",
        true
      );
      return;
    }

    String sql = "SELECT * FROM user WHERE username = ?";

    try {
      ResultSet result = DatabaseQuery.executeSelectQuery(
        sql,
        usernameTf.getText()
      );

      if (result.next()) {
        Methods.displayAlert("User Exists", "The user already exists!", true);
      } else {
        AuthData.id = result.getInt(1);
        AuthData.username = result.getString(2);
        AuthData.isAdmin = result.getBoolean(4);

        DatabaseQuery.executeUserRegisterQuery(
          usernameTf.getText(),
          passwordTf.getText()
        );

        Methods.displayAlert(
          "Successful Registeration",
          "You have been registered successfully!",
          false
        );

        Methods.navigateToMainScene(registerButton);
      }
    } catch (SQLException e) {
      System.out.println("registerButtonClicked() catch");
      e.printStackTrace();
    }
  }
}
