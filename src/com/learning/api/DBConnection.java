package com.learning.api;



import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class DBConnection {

	public DBConnection() {
		// TODO Auto-generated constructor stub
	}

    private static final String URL  = "jdbc:mysql://localhost:3306/sakila";    
    private static final String USER = "root";
    private static final String PASSWORD ="12345678";

//    static {
//        try {
//            // Load MySQL JDBC driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
