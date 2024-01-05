import database.DatabaseConnector;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(Stage primaryStage) {
    try {
      DatabaseConnector.connect();

      Parent authScene = FXMLLoader.load(
        getClass().getResource("/views/AuthScene.fxml")
      );

      Scene scene = new Scene(authScene);
      Image icon = new Image("icons/icon.png"); // Icon by Freepik

      primaryStage.setTitle("AirConnect");
      primaryStage.setScene(scene);
      primaryStage.getIcons().add(icon);
      primaryStage.setResizable(false);
      primaryStage.show();
    } catch (Exception e) {
      System.out.println("start() catch");
      e.printStackTrace();
    } finally {
      try {
        DatabaseConnector.disconnect();
      } catch (SQLException e) {
        System.out.println("start() finally catch");
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
