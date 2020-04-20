package main.ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlTransactionExecutor<T> {
    T execute(Connection connection) throws SQLException;
        }