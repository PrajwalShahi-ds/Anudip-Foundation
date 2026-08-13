package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/library_db";

    private static final String USER = "root";

    private static final String PASSWORD = "MohinI$85";

    public static Connection getConnection() {

        try {

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully");

            return con;

        } catch (Exception e) {

            System.out.println(e.getMessage());

            return null;
        }
    }
}