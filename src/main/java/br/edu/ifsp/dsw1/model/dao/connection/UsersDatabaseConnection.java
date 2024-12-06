package br.edu.ifsp.dsw1.model.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UsersDatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3307/arqdsw1_users";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private UsersDatabaseConnection() {}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
