package br.edu.ifsp.dsw1.model.dao;

public class UsersDaoFactory {
	private UsersDaoType type;
	
	public UsersDaoFactory() {
		type = UsersDaoType.DATABASE;
	}
	
	public UsersDaoFactory(UsersDaoType type) {
		this.type = type;
	}
	
	public UsersDao factory() {
		switch(type) {
			case DATABASE:
				return new DatabaseUsersDao();
			default:
				throw new IllegalArgumentException("Tipo de contato desconhecido: " + type);
		}
	}
	
	public enum UsersDaoType{
			DATABASE;
	}
}
