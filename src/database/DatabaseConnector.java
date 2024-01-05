package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

  private static final String JDBC_URL =
    "jdbc:mysql://localhost:3306/airline_db";
  private static final String USER = "root";
  private static final String PASSWORD = "Omar137924685+";

  private static Connection connection;

  public static Connection connect() throws SQLException {
    if (connection == null || connection.isClosed()) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
      } catch (ClassNotFoundException | SQLException e) {
        System.out.println("connect() catch");
        e.printStackTrace();
      }
    }
    return connection;
  }

  public static void disconnect() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
    }
  }
}
