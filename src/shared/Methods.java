package shared;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Methods {

  static Alert alert;

  public static void displayAlert(
    String title,
    String content,
    boolean isError
  ) {
    alert = new Alert(isError ? AlertType.ERROR : AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }

  public static void navigateToAuth(Button button) {
    try {
      button.getScene().getWindow().hide();

      Parent authScene = FXMLLoader.load(
        Methods.class.getResource("/views/authScene.fxml")
      );

      Stage stage = new Stage();
      Scene scene = new Scene(authScene);
      Image icon = new Image("icons/icon.png"); // Icon by Freepik

      stage.setTitle("AirConnect");
      stage.setScene(scene);
      stage.getIcons().add(icon);
      stage.setResizable(false);
      stage.show();
    } catch (Exception e) {
      System.out.println("navigateToAuth(button) catch");
    }
  }

  public static void navigateToMainScene(Button button) {
    try {
      button.getScene().getWindow().hide();

      Parent mainScene = FXMLLoader.load(
        Methods.class.getResource("/views/ClientScene.fxml")
      );

      Stage stage = new Stage();
      Scene scene = new Scene(mainScene);
      Image icon = new Image("icons/icon.png"); // Icon by Freepik

      stage.setTitle("AirConnect");
      stage.setScene(scene);
      stage.getIcons().add(icon);
      stage.setResizable(false);
      stage.show();
    } catch (Exception e) {
      System.out.println("navigateToMainScene(button) catch");
    }
  }

  public static void SwitchForm(
    String destination,
    Node flightForm,
    Node reservationForm
  ) {
    if (destination == "flights") {
      flightForm.setVisible(true);
      reservationForm.setVisible(false);
    } else if (destination == "reservations") {
      flightForm.setVisible(false);
      reservationForm.setVisible(true);
    }
  }

  public static boolean displayConfirmation(String title, String contentText) {
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationAlert.setTitle(title);
    confirmationAlert.setHeaderText(null);
    confirmationAlert.setContentText(contentText);

    // Get the result of the user's choice
    Stage stage = (Stage) confirmationAlert
      .getDialogPane()
      .getScene()
      .getWindow();
    stage.setAlwaysOnTop(true);
    stage.toFront();

    // Show the confirmation dialog and wait for the user's response
    ButtonType result = confirmationAlert
      .showAndWait()
      .orElse(ButtonType.CANCEL);

    return result == ButtonType.OK;
  }
}
