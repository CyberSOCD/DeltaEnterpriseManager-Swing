package drivers;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.UserConnectionData;

public class DriverDB {
	private String URLConection = "jdbc:oracle:thin:@";
	private String host;
	private UserConnectionData data;
	private String query;
	private Connection connection;
	public DriverDB(UserConnectionData userData, String cons) {
		query = cons;
		data = userData;
		host = data.getDbURL();
		//Crea la conexion
		connection = null;
		try {
//			System.out.println(URLConection + host);
			connection = DriverManager.getConnection(URLConection + host, "DELTA_MNTO_USR", "deltamntousr");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	public String getFirstRowColumn(String column){
		Statement stmt = null;
		String value = "";
		try {
			ResultSet res;
			stmt = connection.createStatement();
			res = stmt.executeQuery(query);
			while (res.next()) {
				value = res.getString(column);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}
}
