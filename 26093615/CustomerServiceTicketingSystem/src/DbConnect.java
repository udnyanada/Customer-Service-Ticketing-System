import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	private static final String URL = "jdbc:mysql://localhost:3306/customerService";
    private static final String USER = "root";
    private static final String PASSWORD = "KimTaehyung100$";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
