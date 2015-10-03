package com.blockingHD.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by MrKickkiller on 3/10/2015.
 */
public class CookieDatabase implements AutoCloseable, IDatabase{

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
