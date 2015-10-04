package com.blockingHD.database;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by MrKickkiller on 4/10/2015.
 *
 * Contains all modification / access methods to the database
 */
public class CookieDataBaseManipulator {

    private IDatabase database;

    public CookieDataBaseManipulator(IDatabase database) {
        this.database = database;
    }

    public int getCookieAmountForPerson(String username){
        Connection conn = database.getConnection();
        username = username.trim();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from cookies where username like ?");
            ps.setString(1,username.trim());
            List<StreamViewer> result =  database.executeSQLStatement(ps);
            return result.get(0).getCookieCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new InvalidParameterException("Couldn't find user in database");
    }

    public boolean getModStatusForPerson(String username){
        Connection conn = database.getConnection();
        username = username.trim();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from cookies where username like ?");
            ps.setString(1,username.trim());
            List<StreamViewer> result =  database.executeSQLStatement(ps);
            return result.get(0).isModStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new InvalidParameterException("Couldn't find user in database");
    }

    public boolean isPersonAlreadyInDatabase(String username){
        Connection conn = database.getConnection();
        username = username.trim();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT  * from cookies where username like ?");
            ps.setString(1,username);
            return !database.executeSQLStatement(ps).isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
