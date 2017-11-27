package gad.manta.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Asistencia {
	private int id_user;
	private int id_quorum;
	private String estado;
	
	public Asistencia(int id, int idquorum, String estado) {
		this.id_user=id;
		this.id_quorum=idquorum;
		this.estado=estado;
	}
	
	public Asistencia(int id_user) {
		this.id_user=id_user;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public int getId_quorum() {
		return id_quorum;
	}
	public void setId_quorum(int id_quorum) {
		this.id_quorum = id_quorum;
	}
	
	
	public int guardarRegistro(Connection connection) {
		String sql = "insert into Asistencia_VE (id_user, id_quorum, estado_asistencia)values(?,?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, id_user);
			instruccion.setInt(2, id_quorum);
			instruccion.setString(3, estado);
			instruccion.execute();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int tomarAsistencia(Connection connection) {
		String sql = "\r\n" + 
				"update Asistencia_VE set estado_asistencia='PRESENTE' where id_user=?";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, id_user);
			instruccion.execute();
			
			return 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		
	}
	
}
