package com.blockingHD.database;

import com.blockingHD.CookieBotMain;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrKickkiller on 3/10/2015.
 * Mimics the database itself
 * Use @executeSQLStatement to manipulate the database
 */
public class CookieDatabase implements AutoCloseable, IDatabase<StreamViewer>{

    static Map<Integer, Boolean> map = new HashMap<>();
    static {
        map.put(1,true);
        map.put(0,false);
    }

    Connection conn;

    public CookieDatabase() {
        try {
            // Get connection to the database
            String url = (String) CookieBotMain.prop.get("databaseURL");
            if (CookieBotMain.devModeOn){
                url = "src/main/resources/" + url;
            }
            System.out.println(url);
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);
            conn.prepareStatement("CREATE TABLE IF NOT EXISTS cookies(username NAME , cookies int, modstatus INT DEFAULT 0) ").execute();

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
                sv.setModStatus(map.get(rs.getInt("modstatus")));
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
