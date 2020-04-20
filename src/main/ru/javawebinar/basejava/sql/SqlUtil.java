package main.ru.javawebinar.basejava.sql;

import main.ru.javawebinar.basejava.model.Resume;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlUtil {
    void doSqlAction(ResultSet set, Resume resume) throws SQLException;
}
