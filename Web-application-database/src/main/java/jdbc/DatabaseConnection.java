package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
  public static Connection getConnection() throws IOException, SQLException {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/geometry", "postgres", "password");
  }
}
