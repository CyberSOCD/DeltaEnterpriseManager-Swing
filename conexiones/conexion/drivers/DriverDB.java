package conexion.drivers;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controlador.common.UserConnectionData;

public class DriverDB {
	private String URLConection = "jdbc:oracle:thin:@";
	private String host;
	private UserConnectionData data;
	private String query;
	private Connection connection;
	public DriverDB(UserConnectionData userData, String query) {
		this.query = query;
		data = userData;
		host = data.getDbURL();
		try {
			openConnection();
			System.out.println(System.currentTimeMillis());
			System.out.println(connection.getSchema());
		} catch (SQLException e) {
			System.out.println("Fallo al establecer conexion con la BBDD");
		}
	}
	
	/**
	 * Constructor generico orientado a ejecutar multiples 
	 * consultas utilizando la misma conexion
	 * 
	 * @param userData
	 */
	public DriverDB(UserConnectionData userData){
		data = userData;
		host = data.getDbURL();
	}
	
	 public void executeStatements(ArrayList<String> queries) throws SQLException{
		try{
			//Establece conexion
			openConnection();
			//Recorre las queries de la lista y las ejecuta
		}catch(SQLException e){
			
		}catch(Exception e){
			
		}finally{
			//Cierra conexion
			closeConnection();
		}
	}
	
	public String getFirstRowColumn(String column) throws SQLException{
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
			throw e;
		}
		return value;
	}
	
	public String getFirstRowColumnNoRaiseExcp(String column){
		Statement stmt = null;
		String value = "";
		try {
			ResultSet res;
			System.out.println(System.currentTimeMillis());
			stmt = connection.createStatement();
			res = stmt.executeQuery(query);
			while (res.next()) {
				value = res.getString(column);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		return value;
	}
	
	private void openConnection() throws SQLException{
		connection = null;
		try {
			connection = DriverManager.getConnection(URLConection + host, "DELTA_MNTO_USR", "deltamntousr");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
