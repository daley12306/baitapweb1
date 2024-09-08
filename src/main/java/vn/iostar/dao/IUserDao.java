package vn.iostar.dao;

import java.util.List;

import vn.iostar.models.UserModel;

public interface IUserDao {
	
	List<UserModel> findAll();
	
	UserModel findByID(int id);
	
	UserModel findByUsername(String username);
	
	void insert(UserModel user);

	UserModel login(String username, String password);
	
	boolean register(String username, String email, String password, String fullname, String image);
	
	boolean checkExistUsername(String username);
	
	boolean checkExistEmail(String email);
	
}
