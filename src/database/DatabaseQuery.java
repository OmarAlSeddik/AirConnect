package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DatabaseQuery {

  public static ResultSet executeSelectQuery(String sql) {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
      connection = DatabaseConnector.connect();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(sql);
    } catch (SQLException e) {
      System.out.println("executeSelectQuery(sql) catch");
      e.printStackTrace();
    }

    return resultSet;
  }

  public static ResultSet executeSelectQuery(String sql, String s1) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql);
      statement.setString(1, s1);
      resultSet = statement.executeQuery();
    } catch (SQLException e) {
      System.out.println("executeSelectQuery(sql, s1) catch");
      e.printStackTrace();
    }

    return resultSet;
  }

  public static ResultSet executeSelectQuery(String sql, String s1, String s2) {
    Connection connection;
    PreparedStatement statement;
    ResultSet resultSet = null;

    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql);
      statement.setString(1, s1);
      statement.setString(2, s2);
      resultSet = statement.executeQuery();
    } catch (SQLException e) {
      System.out.println("executeSelectQuery(sql, s1, s2) catch");
      e.printStackTrace();
    }

    return resultSet;
  }

  public static void executeUserRegisterQuery(
    String username,
    String password
  ) {
    Connection connection = null;
    PreparedStatement statement = null;

    String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql);
      statement.setString(1, username);
      statement.setString(2, password);
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("executeRegisterQuery() catch");
      e.printStackTrace();
    }
  }

  public static void executeFlightRegisterQuery(
    String origin,
    String destination,
    LocalDate departureDate,
    LocalDate arrivalDate
  ) {
    Date sqlDepartureDate = Date.valueOf(departureDate);
    Date sqlArrivalDate = Date.valueOf(arrivalDate);

    Connection connection = null;
    PreparedStatement statement = null;

    String sql =
      "INSERT INTO flight (origin, destination, departure, arrival) VALUES (?, ?, ?, ?)";
    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql);
      statement.setString(1, origin);
      statement.setString(2, destination);
      statement.setDate(3, sqlDepartureDate);
      statement.setDate(4, sqlArrivalDate);

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("executeFlightRegisterQuery() catch");
      e.printStackTrace();
    }
  }

  public static void executeFlightDeleteQuery(int flightId) {
    String sql1 = "DELETE FROM reservation WHERE flight_id = ?";
    String sql2 = "DELETE FROM flight WHERE id = ?";
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql1);
      statement.setInt(1, flightId);
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("executeFlightDeleteQuery() sql1 catch");
      e.printStackTrace();
    }

    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql2);
      statement.setInt(1, flightId);
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("executeFlightDeleteQuery() sql2 catch");
      e.printStackTrace();
    }
  }

  public static void executeFlightUpdateQuery(
    int flightId,
    String origin,
    String destination,
    LocalDate departureDate,
    LocalDate arrivalDate
  ) {
    String sql =
      "UPDATE flight SET origin = ?, destination = ?, departure = ?, arrival = ? WHERE id = " +
      flightId;

    Date sqlDepartureDate = Date.valueOf(departureDate);
    Date sqlArrivalDate = Date.valueOf(arrivalDate);
    Connection connection = null;
    PreparedStatement statement = null;

    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql);
      statement.setString(1, origin);
      statement.setString(2, destination);
      statement.setDate(3, sqlDepartureDate);
      statement.setDate(4, sqlArrivalDate);

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("executeFlightUpdateQuery() catch");
      e.printStackTrace();
    }
  }

  public static void executeFlightReserveQuery(
    Integer userId,
    Integer flightId
  ) {
    Connection connection = null;
    PreparedStatement statement = null;

    String sql = "INSERT INTO reservation (user_id, flight_id) VALUES (?, ?)";
    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql);
      statement.setInt(1, userId);
      statement.setInt(2, flightId);
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("executeRegisterQuery() catch");
      e.printStackTrace();
    }
  }

  public static void executeFlightUnreserveQuery(
    Integer userId,
    Integer flightId
  ) {
    Connection connection = null;
    PreparedStatement statement = null;

    String sql = "DELETE FROM reservation WHERE user_id = ? and flight_id = ?";
    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql);
      statement.setInt(1, userId);
      statement.setInt(2, flightId);
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("executeRegisterQuery() catch");
      e.printStackTrace();
    }
  }

  public static boolean executeCheckReservedQuery(
    Integer userId,
    Integer flightId
  ) {
    String sql =
      "SELECT * FROM reservation WHERE user_id = " +
      userId +
      " AND flight_id = " +
      flightId;

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
      connection = DatabaseConnector.connect();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(sql);
      if (resultSet.next()) return true;
    } catch (SQLException e) {
      System.out.println("executeCheckReservedQuery() catch");
      e.printStackTrace();
    }

    return false;
  }

  public static Integer executeGetSeatCountQuery(Integer flightId) {
    String sql = "SELECT COUNT(user_id) from reservation where flight_id = ?";

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
      connection = DatabaseConnector.connect();
      statement = connection.prepareStatement(sql);
      statement.setInt(1, flightId);
      resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getInt(1);
      }
    } catch (SQLException e) {
      System.out.println("executeSelectQuery(sql) catch");
      e.printStackTrace();
    }

    return 0;
  }
}
