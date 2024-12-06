package database.QueryResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface HandlerInterface <T> {
    T proccess(ResultSet $result) throws SQLException;
}
