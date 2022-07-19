package pt.novasbe.pmc.data;

import java.sql.*;

public class ConnectionFactoryMySql {

    private static final ConnectionFactoryMySql ref = new ConnectionFactoryMySql();
    private ConnectionFactoryMySql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("ERROR: exception loading driver class MySql");
        }
    }

    public static Connection getConnection() throws SQLException {

        /**********PRODUÇÃO**************/

        // nota: este utilizador apenas está autorizado a aceder da máquina web-serv
        //String url = "jdbc:mysql://10.100.1.10:3306/db_faculty_ta";
        //return DriverManager.getConnection(url, "mobility", "HvDZ!Y3&p(VZ9jR}sna?g?F7vt");

       String url = "jdbc:mysql://localhost:3306?useSSL=false";
       return DriverManager.getConnection(url, "root", "trassa1");


       //String url = "jdbc:mysql://10.100.1.10:3306?autoReconnect=true&useSSL=false";
       //return DriverManager.getConnection(url, "rui.spranger", "Tretas#2019");
       //String url = "jdbc:mysql://localhost:3306?autoReconnect=true&useSSL=false";
       //return DriverManager.getConnection(url, "root", "trassa1");

        // Acesso à instancia de Bidding - Azure
       //String url = "jdbc:mysql://10.100.1.5:3306/pt.novasbe.pmc.bidding";
       //return DriverManager.getConnection(url, "pt.novasbe.pmc.bidding", "##BiddingAzure");

        // Acesso à instancia Azure
        //String url = "jdbc:mysql://10.100.1.5:3306/pt.novasbe.pmc.bidding?autoReconnect=true&useSSL=false";
        //return DriverManager.getConnection(url, "pt.novasbe.pmc.bidding", "##BiddingAzure");

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
