package br.edu.ifsp.dsw1.model.dao;

import br.edu.ifsp.dsw1.model.entity.Users;

public interface UsersDao {
	boolean create(Users user);
	boolean retrieve(String email, String senha);
}
