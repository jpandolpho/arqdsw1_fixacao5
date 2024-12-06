package br.edu.ifsp.dsw1.model.dao;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.ifsp.dsw1.model.dao.connection.UsersDatabaseConnection;
import br.edu.ifsp.dsw1.model.entity.Users;

public class DatabaseUsersDao implements UsersDao {

	@Override
	public boolean create(Users user) {
		if(user != null) {
			String senha = user.getPassword();
			try {
				MessageDigest cripto = MessageDigest.getInstance("SHA-256");
				byte digerido[] = cripto.digest(senha.getBytes());
				StringBuilder hexString = new StringBuilder();
				for (byte b : digerido) {
					hexString.append(String.format("%02X", 0xFF & b));
				}
				senha = hexString.toString();
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			var sql = "INSERT INTO users(name,email,password) VALUES ('"
					+ user.getNome() + "','"
					+ user.getEmail() + "','"
					+ senha + "');";
			int rows = -1;
			try {
				var connection = UsersDatabaseConnection.getConnection();
				var statement = connection.createStatement();
				
				rows = statement.executeUpdate(sql);
				
				statement.close();
				connection.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			return rows > 0;
		}
		return false;
	}

	@Override
	public boolean retrieve(String email, String senha) {
		boolean valid = false;
		if(email!=null && !email.isEmpty() && senha!=null && !senha.isEmpty()) {
			var sql = "SELECT password FROM users WHERE email='"+email+"';";
			try {
				var connection = UsersDatabaseConnection.getConnection();
				var statement = connection.createStatement();
				
				ResultSet result = statement.executeQuery(sql);
				
				String transformed;
				if(result.next()) {
					try {
						MessageDigest cripto = MessageDigest.getInstance("SHA-256");
						byte digerido[] = cripto.digest(senha.getBytes());
						StringBuilder hexString = new StringBuilder();
						for (byte b : digerido) {
							hexString.append(String.format("%02X", 0xFF & b));
						}
						transformed = hexString.toString();
					}catch(Exception e) {
						e.printStackTrace();
						transformed="";
					}
					valid = transformed.equals(result.getString("password"));
				}
				statement.close();
				connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return valid;
	}

}
