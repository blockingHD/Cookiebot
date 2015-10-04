package com.blockingHD.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by MrKickkiller on 4/10/2015.
 */
public interface IDatabase {

    public List<StreamViewer> executeSQLStatement(PreparedStatement preparedStatement);

    public void executeSQLUpdate(PreparedStatement preparedStatement);

    public Connection getConnection();
}
