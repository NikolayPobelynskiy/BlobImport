package database;

import database.QueryResultHandler.HandlerInterface;

import java.sql.*;

public class MysqlConnection {

    private String url;
    private String user;
    private String password;

    public MysqlConnection() {
        /**
         * @todo Вынести в конфиги
         */
        this.url = "jdbc:mysql://localhost:3411/example";
        this.user = "username";
        this.password = "password087";
    }

    public <T> T getResult(String query, HandlerInterface<T> handler)
    {
        try {
            Statement statement = this.createStatement();
            ResultSet result = statement.executeQuery(query);
            T handlerResult = handler.proccess(result);
            result.close();
            statement.getConnection().close();
            statement.close();

            return handlerResult;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    private Statement createStatement() throws SQLException {
        java.sql.Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
        return connection.createStatement();
    }
}
