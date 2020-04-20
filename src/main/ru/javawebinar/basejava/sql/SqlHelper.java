package main.ru.javawebinar.basejava.sql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory factory;
    private static final Logger LOGGER = LogManager.getLogger("sqlLogger");

    public SqlHelper(ConnectionFactory factory) {
        this.factory = factory;
    }

    public <T> T doSQL(String sql, SqlExecutor<T> executor) {
        try (Connection con = factory.getConnection()) {
            PreparedStatement statement = con.prepareStatement(sql);
            return executor.execute(statement);
        } catch (SQLException e) {
            LOGGER.warn("SQL query " + sql + " fall with exception");
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T doTransactSQL(SqlTransactionExecutor<T> executor) {
        try (Connection connection = factory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T res = executor.execute(connection);
                connection.commit();
                return res;
            } catch (SQLException e) {
                LOGGER.warn("Transaction fall with exception");
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }


}
