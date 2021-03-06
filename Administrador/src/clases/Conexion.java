package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import gad.manta.common.data_configuracion;

public class Conexion {
	
	private  Connection connection;
	private  String url = "jdbc:postgresql://"+data_configuracion.ipBaseDatos+"/"+data_configuracion.nombre_bd+"";
	private  String user = data_configuracion.usu_db;
	private  String password = data_configuracion.conta_usu;
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void establecerConexion() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url,user,password);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cerrarConexion() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
