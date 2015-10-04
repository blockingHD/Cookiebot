package com.blockingHD.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrKickkiller on 3/10/2015.
 * Mimics the database itself
 * Use @executeSQLStatement to manipulate the database
 */
public class CookieDatabase implements AutoCloseable, IDatabase<StreamViewer>{

    Connection conn;

    public CookieDatabase() {
        try {
            // Get connection to the database
            // TODO:Make settable in config file
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/blockingHD/databaseCookies.sqlite");

            // Clear the current table (For testing purposes)
            // TODO: Make table persistent
            // Mod status: 0 == Normal, 1 == Mod ; Default value == 0;
            conn.prepareStatement("drop TABLE if EXISTS cookies").execute();
            conn.prepareStatement("CREATE TABLE cookies(username NAME, cookies int, modstatus INT DEFAULT 0)").execute();

            // Add a normal standard value (FOR TESTING)
            conn.prepareStatement("INSERT into cookies(username,cookies) VALUES('MrKickkiller',14)").execute();
            conn.prepareStatement("INSERT into cookies(username,cookies) VALUES('BlockingHD',18)").execute();
            conn.prepareStatement("INSERT into cookies(username,cookies) VALUES('Quetzi',22)").execute();
            conn.prepareStatement("INSERT into cookies(username,cookies) VALUES('K4',26)").execute();
            conn.prepareStatement("INSERT into cookies(username,cookies) VALUES('Loneztar',30)").execute();
            conn.prepareStatement("INSERT into cookies(username,cookies) VALUES('iMarBot',34)").execute();
            conn.prepareStatement("INSERT into cookies(username,cookies) VALUES('Danyo',38)").execute();
            conn.prepareStatement("INSERT into cookies(username,cookies) VALUES('Amadornes',42)").execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * Used to gather information out of the database.
    * PreparedStatement should return a ResultSet.
     */
    @Override
    public List<StreamViewer> executeSQLStatement(PreparedStatement preparedStatement) {
        List<StreamViewer> output = new ArrayList<>();
        try {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                StreamViewer sv = new StreamViewer(rs.getString("username"));
                if (rs.getInt("modstatus") == 0){
                    sv.setModStatus(false);
                }else {
                    sv.setModStatus(true);
                }
                sv.setCookieCount(rs.getInt("cookies"));
                output.add(sv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }


    /*
    * Used to update items in the database.
    * PreparedStatement shall not return a ResultSet.
     */
    @Override
    public void executeSQLUpdate(PreparedStatement preparedStatement) {
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return conn;
    }

    //<editor-fold desc="Cleanup code: Memory leak-prevention">

    /*
    * Cleanup code
     */
    @Override
    public void close() throws Exception {
        conn.close();
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
    //</editor-fold>
}
