package com.blockingHD.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by MrKickkiller on 4/10/2015.
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
        return Integer.MIN_VALUE ;
    }

}
