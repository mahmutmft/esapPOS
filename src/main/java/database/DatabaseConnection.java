package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        String url = "jdbc:postgresql://localhost:5432/esap";
        String username = "postgres";
        String password = dotenv.get("ESAP_DB_PASSWORD");

        return DriverManager.getConnection(url, username, password);
    }
}