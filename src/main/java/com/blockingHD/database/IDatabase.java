package com.blockingHD.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by MrKickkiller on 4/10/2015.
 */
public interface IDatabase<T> {

    List<T> executeSQLStatement(PreparedStatement preparedStatement);

    void executeSQLUpdate(PreparedStatement preparedStatement);

    Connection getConnection();
}
