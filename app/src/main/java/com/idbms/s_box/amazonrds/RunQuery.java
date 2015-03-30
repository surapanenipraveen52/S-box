package com.idbms.s_box.amazonrds;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by praveen on 3/24/2015.
 */
public class RunQuery {
    private static final String url = "jdbc:mysql://mydbinstance.cfhhwlnlw8kg.us-west-2.rds.amazonaws.com:3306/mydb?user=chands4uonly&password=id09100775";
    private static final String TAG ="RunUpdateQuery" ;
    public static Connection con = null;
    public static ResultSet resultSet=null;
    public static Statement st =null;
    public boolean openRDSConnection(){
        if(null!=con){
            closeConnection();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url);
            Log.v(TAG, "Connection opened");
            return true;
        } catch (ClassNotFoundException e) {
            Log.v(TAG, e.getMessage());
            closeConnection();
            return false;
        }catch (SQLException se){
            Log.v(TAG, se.getMessage());
            closeConnection();
            return false;
        }

    }
    public boolean runInsert(String query){
        Log.v(TAG, " Insert Query is "+query);
        try {
            st = con.createStatement();
            Log.v(TAG, "Inserting in to DB");
            st.execute(query);
            return true;
        }
         catch (SQLException se){
             Log.v(TAG, se.getMessage());
             closeConnection();
             return false;
        }

    }
    public ResultSet runSelect(String query){
        try {
            st = con.createStatement();
            Log.v(TAG, "Query is "+query);
            resultSet=st.executeQuery(query);
            Log.v(TAG, "result set is  "+resultSet.toString());
        }catch (SQLException se){
            closeConnection();
            return null;
        }
        return resultSet;
    }
    public void closeConnection(){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {

        }
    }
}
