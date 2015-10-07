package com.blockingHD.database;

import com.blockingHD.CookieBotMain;
import com.blockingHD.exceptions.OutOfCookieException;
import com.blockingHD.exceptions.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrKickkiller on 4/10/2015.
 *
 * Contains all modification / access methods to the database
 */
public class CookieDataBaseManipulator {

    static Map<Boolean,Integer> changeMap = new HashMap<>();
    static {
        changeMap.put(true,1);
        changeMap.put(false,0);
    }

    private IDatabase database;

    public CookieDataBaseManipulator(IDatabase database) {
        this.database = database;
    }

    /*
    * Get the amount of cookies a person has from the database.
     */
    public int getCookieAmountForPerson(String username) throws UserNotFoundException {
        Connection conn = database.getConnection();
        username = username.trim();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from cookies where username like ?");
            ps.setString(1,username.trim());
            List<StreamViewer> result = (List<StreamViewer>)  database.executeSQLStatement(ps);
            if (result.size() == 0){
                throw new UserNotFoundException("Couldn't find user in database");
            }
            return result.get(0).getCookieCount();
        } catch (SQLException e) {
            e.printStackTrace();
            CookieBotMain.printStaticMessageToAuthors();
        }
        throw new UserNotFoundException("Couldn't find user in database");
    }

    /*
    * Get the modstatus from a specific user.
     */
    public boolean getModStatusForPerson(String username) throws UserNotFoundException {
        Connection conn = database.getConnection();
        username = username.trim();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * from cookies where username like ?");
            ps.setString(1,username.trim());
            List<StreamViewer> result =  (List<StreamViewer>) database.executeSQLStatement(ps);
            if (result.size() == 0){
                throw new UserNotFoundException("Couldn't find user in database");
            }
            return result.get(0).isModStatus();
        } catch (SQLException e) {
            e.printStackTrace();
            CookieBotMain.printStaticMessageToAuthors();
        }
        throw new UserNotFoundException("Couldn't find user in database");
    }

    /*
    * Check if the person's name is already recorded in the database.
     */
    public boolean isPersonAlreadyInDatabase(String username){
        Connection conn = database.getConnection();
        username = username.trim();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT  * from cookies where username like ?");
            ps.setString(1,username);
            return !database.executeSQLStatement(ps).isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            CookieBotMain.printStaticMessageToAuthors();
        }
        return false;
    }

    public boolean updateModStatus(String username, boolean modstatus){
        try {
            PreparedStatement ps = database.getConnection().prepareStatement("UPDATE cookies set modstatus=? where username like ?");
            ps.setInt(1,changeMap.get(modstatus));
            ps.setString(2,username.trim());
            database.executeSQLUpdate(ps);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            CookieBotMain.printStaticMessageToAuthors();
        }
        return false;
    }

    public boolean addCookiesToUser(String username, int delta){
        int current;
        try {
            current = getCookieAmountForPerson(username.trim());
        } catch (UserNotFoundException e) {
            System.out.println(username + " was not found in the database. Check the spelling and try again.");
            return false;
        }

        Connection conn = database.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE cookies set cookies=? where username like ?");
            ps.setInt(1, current + delta);
            ps.setString(2, username.trim());
            database.executeSQLUpdate(ps);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            CookieBotMain.printStaticMessageToAuthors();
        }
        return false;
    }

    public boolean takeCookiesFromUser(String username, int delta) throws OutOfCookieException {
        int current;

        try {
            current = getCookieAmountForPerson(username.trim());
        } catch (UserNotFoundException e) {
            System.out.println(username.trim() + " was not found in the database. Check the spelling and try again");
            return false;
        }
        if (current - delta < 0){
            throw new OutOfCookieException("You don't have enough cookies to buy this!");
        }else {
            Connection conn = database.getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE cookies set cookies=? where username like ?");
                ps.setInt(1, current - delta);
                ps.setString(2, username.trim());
                database.executeSQLUpdate(ps);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                CookieBotMain.printStaticMessageToAuthors();
            }
            return false;
        }
    }

    public void addCookiesToAllCurrentViewers(ArrayList<String> usernames,int bonuscookies){
        for (String s : usernames){
            addCookiesToUser(s, bonuscookies);
        }
    }

    public boolean initPersonInDatabase(String username, boolean modstatus){
        Connection conn = database.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO cookies VALUES (?,?,?)");
            ps.setString(1,username.trim());
            ps.setInt(2,0);
            ps.setInt(3,changeMap.get(modstatus));
            database.executeSQLUpdate(ps);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            CookieBotMain.printStaticMessageToAuthors();

        }
        return false;
    }

    public boolean initPersonInDatabase(String username){
        return this.initPersonInDatabase(username,false);
    }
}
