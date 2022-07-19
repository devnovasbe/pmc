package pt.novasbe.pmc.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 *@author rui.spranger
 */

public class updateMySql {

    // Objectos de acesso â BD.
    Connection connection;
    Statement statement;
    ResultSet resultSet;


    int result = 0;
    boolean rst = true;

    public  boolean actualizaDados (String sql) {

        try {
            statement = getStatement();
            result = statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
            rst = false;
            return rst;
        } finally {
            close();
        }

        return rst;
    }


    private Statement getStatement() throws SQLException {
        connection = ConnectionFactoryMySql.getConnection();
        return connection.createStatement();
    }

    void close() {
        ConnectionFactoryMySql.close(resultSet);
        ConnectionFactoryMySql.close(statement);
        ConnectionFactoryMySql.close(connection);
    }

}
