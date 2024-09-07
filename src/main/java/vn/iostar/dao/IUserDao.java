package vn.iostar.dao;

import java.util.List;

import vn.iostar.models.UserModel;

public interface IUserDao {
	
	List<UserModel> findAll();
	
	UserModel findByID();
	
	void insert(UserModel user);

}
