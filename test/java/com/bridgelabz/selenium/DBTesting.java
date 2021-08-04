package com.bridgelabz.selenium;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.bridgelabz.selenium.Base;
import java.sql.Connection;


import java.sql.*;



public class DBTesting extends Base {

    private static String root;
    private static String mysql;
    public int count;
    //    private Connection con;
    private static Connection con;
    static Connection connection = null;                                  // Connection object
    private static Statement statement;                                   // Statement object
    public static String DB_URL = "jdbc:mysql://localhost/spotify";       // Constant for Database URL

    @BeforeTest
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");                         // Database connection
        // Get connection to DB
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return con;
    }

    @Test(priority = 0)
    public void get_table_data() throws ClassNotFoundException, SQLException {

        Connection con = this.getConnection();
        Statement statement = con.createStatement();
        String query = "select * from User";     // Get the contents of user table from DB
        System.out.println("/*************/");
        ResultSet res = statement.executeQuery(query);

        while (res.next()) {
            int user_id = res.getInt(1);
            String user_name = res.getString(2);
            String user_email = res.getString(3);
            String user_password = res.getString(4);
            System.out.println(user_id + " " + user_name + "" + user_email + "" + user_password);
            count++;
        }

    }

    @Test(priority = 1)
    public void insert_table_data() throws ClassNotFoundException, SQLException {
        con = this.getConnection();
        PreparedStatement pst = con.prepareStatement("insert into User values(?,?,?,?)");
        pst.setInt(1, 4000);
        pst.setString(2, "pappu");
        pst.setString(3, "pappu23@gmail.com");
        pst.setString(4, "pappu@589");
        pst.executeUpdate();
        get_table_data();
    }


    @Test(priority = 2)
    public void update_table_data() throws ClassNotFoundException, SQLException {
        con = this.getConnection();
        PreparedStatement pst = con.prepareStatement("update User set user_name = ? where user_id = ?");

        pst.setString(1, "prasad");

        pst.setInt(2, 4000);


        pst.executeUpdate();
        get_table_data();
    }

    @Test(priority = 3)
    public void delete_row_from_table() throws ClassNotFoundException, SQLException {
        con = this.getConnection();
        PreparedStatement pst = con.prepareStatement("Delete from User where user_id = ?");
        pst.setInt(1, 4000);
        pst.executeUpdate();
        get_table_data();
    }

    @AfterTest
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.close();          // Close DB connection
        }
    }
}

