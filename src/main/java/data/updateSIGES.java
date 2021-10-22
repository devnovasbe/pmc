package data;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
* @author rui.spranger >:-> MarsAttacks
*/

public class updateSIGES {

    // Objectos de acesso â BD.
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    int result = 0;
    boolean rst = true;

    public  boolean actualizaDados (String sql){

        try {
            statement = getStatement();
            result = statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
            rst = false;

        } finally {
            close();
        }

        return rst;

    }// fim método


    private Statement getStatement() throws SQLException {
        connection = ConnectionFactorySIGES.getConnection();
        return connection.createStatement();
    }

    void close() {
        ConnectionFactorySIGES.close(resultSet);
        ConnectionFactorySIGES.close(statement);
        ConnectionFactorySIGES.close(connection);
    }

} // fim classe
