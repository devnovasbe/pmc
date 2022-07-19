package pt.novasbe.pmc.data;

import java.sql.*;

public class ConnectionFactorySIGES {


    private static final ConnectionFactorySIGES ref = new ConnectionFactorySIGES();
    private ConnectionFactorySIGES() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

        }
        catch (ClassNotFoundException e) {
            System.out.println("ERROR: exception loading driver class ORACLE");
        }
    }

    public static Connection getConnection() throws SQLException {


       String url = "jdbc:oracle:thin:@172.16.2.1:1521:SIGES"; //*******************
        // String url = "jdbc:oracle:thin:@10.100.1.12:1521:SIGES"; //réplica
        return DriverManager.getConnection(url, "admin", "8YVNNN5U9ZADP5M2");

    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        }
        catch (Exception ignored) {}
    }

    public static void close(Statement stmt) {
        try {
            stmt.close();
        }
        catch (Exception ignored) {}
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        }
        catch (Exception ignored) {}
    }


} // fim classe
