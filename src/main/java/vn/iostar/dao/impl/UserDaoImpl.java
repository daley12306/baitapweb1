package vn.iostar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iostar.configs.DBConnectSQL;
import vn.iostar.dao.IUserDao;
import vn.iostar.models.UserModel;

public class UserDaoImpl extends DBConnectSQL implements IUserDao {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	
	@Override
	public List<UserModel> findAll() {
		
		String sql = "select * from users";
		
		List<UserModel> list = new ArrayList<UserModel>();
		
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new UserModel(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("fullname"),
						rs.getString("image")));
			}
			return list;
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserModel findByID(int id) {
		String sql = "select * from users where users.id = ?";
		
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return new UserModel(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("fullname"),
						rs.getString("image"));
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public UserModel findByUsername(String username) {
		String sql = "select * from users where username = ?";
		
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return new UserModel(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("fullname"),
						rs.getString("image"));
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(UserModel user) {
		
		String sql = "INSERT INTO users(id, username, email, password, fullname, image) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			conn = super.getConnection();
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, user.getId());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());;
			ps.setString(5, user.getFullname());;
			ps.setString(6, user.getImage());;
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.findByUsername(username);
		if(user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}
	
	@Override
	public boolean register(String username, String email, String password, String fullname, String image) {
		if(this.checkExistUsername(username) || this.checkExistEmail(email))
			return false;
		this.insert(new UserModel(username, email, password, fullname, image));
		return true;
	}

	@Override
	public boolean checkExistUsername(String username) {
		String sql = "select * from users where username = ?";
		
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkExistEmail(String email) {
		String sql = "select * from users where email = ?";
		
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		UserDaoImpl userDao = new UserDaoImpl();
		
//		INSERT
//		userDao.insert(new UserModel(3, "abc", "abc@gmail.com", "123", "ABC", ""));
		
//		FINDBYID
//		System.out.println(userDao.findByID(1));

//		LOGIN
//		System.out.println(userDao.login("abc", "123"));
//		System.out.println(userDao.login("hello", "123"));
		
		
//		REGISTER
		System.out.println(userDao.register("abc", "abcd@gmail.com", "123", "ABC", ""));
		System.out.println(userDao.register("abcd", "abc@gmail.com", "123", "ABCD", ""));
		System.out.println(userDao.register("abcd", "abcd@gmail.com", "123", "ABCD", ""));
		
//		FINDALL
		List<UserModel> list = userDao.findAll();
		
		for (UserModel user : list) {
			System.out.println(user);
		}
	}
}
