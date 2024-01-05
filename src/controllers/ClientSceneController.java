package controllers;

import auth.AuthData;
import database.DatabaseQuery;
import database.Flight;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import shared.Methods;

public class ClientSceneController implements Initializable {

  @FXML
  private VBox flightForm;

  @FXML
  private TextField flightSearchField;

  @FXML
  private TableView<Flight> flightTable;

  @FXML
  private TableColumn<Flight, Date> flightTableArrival;

  @FXML
  private TableColumn<Flight, Integer> flightTableSeats;

  @FXML
  private TableColumn<Flight, Date> flightTableDeparture;

  @FXML
  private TableColumn<Flight, String> flightTableDestination;

  @FXML
  private TableColumn<Flight, Integer> flightTableId;

  @FXML
  private TableColumn<Flight, String> flightTableOrigin;

  @FXML
  private TableColumn<Flight, String> flightTableReserved;

  @FXML
  private TableView<Flight> reservationTable;

  @FXML
  private TableColumn<Flight, Date> reservationTableArrival;

  @FXML
  private TableColumn<Flight, Integer> reservationTableSeats;

  @FXML
  private TableColumn<Flight, Date> reservationTableDeparture;

  @FXML
  private TableColumn<Flight, String> reservationTableDestination;

  @FXML
  private TableColumn<Flight, Integer> reservationTableId;

  @FXML
  private TableColumn<Flight, String> reservationTableOrigin;

  @FXML
  private Button flightsButton;

  @FXML
  private Button logoutButton;

  @FXML
  private VBox reservationForm;

  @FXML
  private TextField reservationSearchField;

  @FXML
  private Button reservationsButton;

  @FXML
  private Button addFlightButton;

  @FXML
  private Button reserveFlightButton;

  @FXML
  private Button deleteFlightButton;

  @FXML
  private Button updateFlightButton;

  @FXML
  private DatePicker arrivalTf;

  @FXML
  private DatePicker departureTf;

  @FXML
  private TextField originTf;

  @FXML
  private TextField destinationTf;

  private ObservableList<Flight> flightList;
  private ObservableList<Flight> reservationList;

  void search(boolean isFlightMenu) {
    FilteredList<Flight> filter = new FilteredList<>(
      isFlightMenu ? flightList : reservationList,
      e -> true
    );

    TableView<Flight> table = isFlightMenu ? flightTable : reservationTable;

    TextField searchField = isFlightMenu
      ? flightSearchField
      : reservationSearchField;

    searchField
      .textProperty()
      .addListener((observable, oldValue, newValue) -> {
        filter.setPredicate(predicateFlight -> {
          if (newValue == null || newValue.isEmpty()) {
            return true;
          }

          String searchKey = newValue.toLowerCase();

          return (
            predicateFlight
              .getId()
              .toString()
              .toLowerCase()
              .contains(searchKey) ||
            predicateFlight.getOrigin().toLowerCase().contains(searchKey) ||
            predicateFlight
              .getDestination()
              .toLowerCase()
              .contains(searchKey) ||
            (
              predicateFlight.getDeparture() != null &&
              predicateFlight
                .getDeparture()
                .toString()
                .toLowerCase()
                .contains(searchKey)
            ) ||
            (
              predicateFlight.getArrival() != null &&
              predicateFlight
                .getArrival()
                .toString()
                .toLowerCase()
                .contains(searchKey)
            )
          );
        });
      });

    SortedList<Flight> sortedList = new SortedList<>(filter);
    sortedList.comparatorProperty().bind(table.comparatorProperty());
    table.setItems(sortedList);
  }

  @FXML
  void flightsButtonClicked(ActionEvent event) {
    Methods.SwitchForm("flights", flightForm, reservationForm);
    displayFlightListData();
    search(true);
  }

  @FXML
  void logoutButtonClicked(ActionEvent event) {
    Methods.navigateToAuth(logoutButton);
  }

  @FXML
  void reservationsButtonClicked(ActionEvent event) {
    Methods.SwitchForm("reservations", flightForm, reservationForm);
    displayReservationListData();
    search(false);
  }

  @FXML
  void addFlightButtonClicked(ActionEvent event) {
    if (
      originTf.getText().isEmpty() ||
      destinationTf.getText().isEmpty() ||
      arrivalTf.getValue() == null ||
      departureTf.getValue() == null
    ) {
      Methods.displayAlert(
        "Missing Fields",
        "Please fill all the fields!",
        true
      );
      return;
    }

    DatabaseQuery.executeFlightRegisterQuery(
      originTf.getText(),
      destinationTf.getText(),
      departureTf.getValue(),
      arrivalTf.getValue()
    );

    displayFlightListData();

    Methods.displayAlert(
      "Successful Registeration",
      "The flight have been registered successfully!",
      false
    );
  }

  @FXML
  void deleteFlightButtonClicked(ActionEvent event) {
    Flight selectedFlight = flightTable.getSelectionModel().getSelectedItem();

    if (selectedFlight == null) {
      Methods.displayAlert(
        "No Flight Selected",
        "Please select a flight to delete.",
        true
      );
      return;
    }

    boolean confirmDelete = Methods.displayConfirmation(
      "Confirm Deletion",
      "Are you sure you want to delete the selected flight?"
    );

    if (confirmDelete) {
      flightList.remove(selectedFlight);
      DatabaseQuery.executeFlightDeleteQuery(selectedFlight.getId());
      Methods.displayAlert(
        "Deletion Successful",
        "Flight deleted successfully.",
        false
      );
    }
  }

  @FXML
  void updateFlightButtonClicked(ActionEvent event) {
    Flight selectedFlight = flightTable.getSelectionModel().getSelectedItem();

    if (selectedFlight == null) {
      Methods.displayAlert(
        "No Flight Selected",
        "Please select a flight to update.",
        true
      );
      return;
    }

    if (
      originTf.getText().isEmpty() ||
      destinationTf.getText().isEmpty() ||
      arrivalTf.getValue() == null ||
      departureTf.getValue() == null
    ) {
      Methods.displayAlert(
        "Missing Fields",
        "Please fill all the fields!",
        true
      );
      return;
    }

    boolean confirmUpdate = Methods.displayConfirmation(
      "Confirm Update",
      "Are you sure you want to update the selected flight?"
    );

    if (confirmUpdate) {
      DatabaseQuery.executeFlightUpdateQuery(
        selectedFlight.getId(),
        originTf.getText(),
        destinationTf.getText(),
        departureTf.getValue(),
        arrivalTf.getValue()
      );
      Methods.displayAlert(
        "Update Successful",
        "Flight updated successfully.",
        false
      );

      displayFlightListData();
    }
  }

  @FXML
  void reserveFlightButtonClicked(ActionEvent event) {
    Flight selectedFlight = flightTable.getSelectionModel().getSelectedItem();
    DatabaseQuery.executeFlightReserveQuery(
      AuthData.id,
      selectedFlight.getId()
    );
  }

  ObservableList<Flight> listData(boolean isFlightMenu) {
    ObservableList<Flight> listData = FXCollections.observableArrayList();

    String sql = isFlightMenu
      ? "select * from flight"
      : "SELECT flight.* FROM flight JOIN reservation ON flight.id = reservation.flight_id WHERE reservation.user_id = " +
      AuthData.id;

    try {
      ResultSet result = DatabaseQuery.executeSelectQuery(sql);

      while (result.next()) {
        listData.add(
          new Flight(
            result.getInt("id"),
            result.getString("origin"),
            result.getString("destination"),
            result.getDate("departure"),
            result.getDate("arrival")
          )
        );
      }
    } catch (SQLException e) {
      System.out.println("listData() catch");
      e.printStackTrace();
    }

    return listData;
  }

  void displayFlightListData() {
    flightList = listData(true);
    flightTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
    flightTableOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
    flightTableDestination.setCellValueFactory(
      new PropertyValueFactory<>("destination")
    );
    flightTableDeparture.setCellValueFactory(
      new PropertyValueFactory<>("departure")
    );
    flightTableArrival.setCellValueFactory(
      new PropertyValueFactory<>("arrival")
    );
    flightTable.setItems(flightList);
  }

  void displayReservationListData() {
    reservationList = listData(false);
    reservationTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
    reservationTableOrigin.setCellValueFactory(
      new PropertyValueFactory<>("origin")
    );
    reservationTableDestination.setCellValueFactory(
      new PropertyValueFactory<>("destination")
    );
    reservationTableDeparture.setCellValueFactory(
      new PropertyValueFactory<>("departure")
    );
    reservationTableArrival.setCellValueFactory(
      new PropertyValueFactory<>("arrival")
    );
    reservationTable.setItems(reservationList);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    displayFlightListData();
    search(true);
  }
}
