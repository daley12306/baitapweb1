package vn.iostar.configs;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectSQL {
	private final String serverName = "localhost";
	private final String dbName = "ltweb";
	private final String portNumber = "1433";
	private final String instance = "";
	private final String userID = "sa";
	private final String password = "12345678";

	public Connection getConnection() {
		Connection conn = null;
		try {
			String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance + ";databaseName=" + dbName;
			if (instance == null || instance.trim().isEmpty()) {
				url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName;
			}
			
			// Initialize the connection
			conn = DriverManager.getConnection(url, userID, password);
			
			// Check if the connection is successful
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println("Driver name: " + dm.getDriverName());
				System.out.println("Driver version: " + dm.getDriverVersion());
				System.out.println("Product name: " + dm.getDatabaseProductName());
				System.out.println("Product version: " + dm.getDatabaseProductVersion());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return conn;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(new DBConnectSQL().getConnection());			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
