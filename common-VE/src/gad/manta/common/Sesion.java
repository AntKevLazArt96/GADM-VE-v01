package gad.manta.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class Sesion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7128567888400860325L;
	private String convocatoria;
	private String description;
	private String tipo_sesion;
	private Date fechaRegistro;
	private Date fechaIntervencion;
	private String horaIntervencion;
	private int id_quorum;
	private int id_pdf;

	public Sesion(String convocatoria, String description, String tipo_sesion, Date fechaRegistro,
			Date fechaIntervencion, String horaIntervencion,int id_pdf) {
		super();
		this.convocatoria = convocatoria;
		this.description = description;
		this.tipo_sesion = tipo_sesion;
		this.fechaRegistro = fechaRegistro;
		this.fechaIntervencion = fechaIntervencion;
		this.horaIntervencion = horaIntervencion;
		this.id_pdf = id_pdf;
		
	}



	public String getTipo_sesion() {
		return tipo_sesion;
	}


	public void setTipo_sesion(String tipo_sesion) {
		this.tipo_sesion = tipo_sesion;
	}





	public String getConvocatoria() {
		return convocatoria;
	}


	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public Date getFechaIntervencion() {
		return fechaIntervencion;
	}


	public void setFechaIntervencion(Date fechaIntervencion) {
		this.fechaIntervencion = fechaIntervencion;
	}


	public String getHoraIntervencion() {
		return horaIntervencion;
	}


	public void setHoraIntervencion(String horaIntervencion) {
		this.horaIntervencion = horaIntervencion;
	}


	public int getId_quorum() {
		return id_quorum;
	}


	public void setId_quorum(int id_quorum) {
		this.id_quorum = id_quorum;
	}


	public int getId_pdf() {
		return id_pdf;
	}


	public void setId_pdf(int id_pdf) {
		this.id_pdf = id_pdf;
	}

	public String toString() {
		return convocatoria;
	}
	public Sesion(String convocatoria, String description, Date registro, Date intervencion,String hora) {
		this.convocatoria=convocatoria;
		this.description= description;
		this.fechaRegistro=registro;
		this.fechaIntervencion= intervencion;
		this.horaIntervencion= hora;
	}
	public Sesion(String convocatoria, String description, Date registro, Date intervencion,String hora,  int idPdf) {
		this.convocatoria=convocatoria;
		this.description= description;
		this.fechaRegistro=registro;
		this.fechaIntervencion= intervencion;
		this.horaIntervencion= hora;
		this.id_pdf = idPdf;
	}

	public Sesion(String convocatoria, String titulo) {
	
		this.convocatoria=convocatoria;
		this.description= titulo;
	}
	
	public Sesion(int id_quorum, String convocatoria) {
		this.id_quorum=id_quorum;
		this.convocatoria=convocatoria;
	}
	
	
	public Sesion(String convocatoria, String description, int id_pdf) {
		this.convocatoria = convocatoria;
		this.description = description;
		this.id_pdf = id_pdf;
	}


	public Sesion(String convocatoria, String description, String tipo_sesion, Date fechaRegistro,
			Date fechaIntervencion, String horaIntervencion) {
		this.convocatoria = convocatoria;
		this.description = description;
		this.tipo_sesion = tipo_sesion;
		this.fechaRegistro = fechaRegistro;
		this.fechaIntervencion = fechaIntervencion;
		this.horaIntervencion = horaIntervencion;
	}



	

	public String guardarRegistro(Connection connection) {
		String sql = "select * from ingresar_sesion(?,?,?,?,?,?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setString(1, getConvocatoria());
			instruccion.setString(2, getDescription());
			instruccion.setString(3, getTipo_sesion());
			instruccion.setDate(4, getFechaRegistro());
			instruccion.setDate(5, getFechaIntervencion());
			instruccion.setString(6, getHoraIntervencion());
			instruccion.setInt(7, getId_pdf());
			instruccion.execute();
			
			return getConvocatoria();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void llenarInformacion_sesion(Connection connection, ObservableList<Sesion> sesio) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select * from Sesion_VE");
			while(resultado.next()) {
				sesio.add(new Sesion(resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getDate(4),resultado.getDate(5),resultado.getString(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static void llenarInformacion(Connection connection, ObservableList<Usuario> users) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select *from User_VE where position_user='concejal'");
			while(resultado.next()) {
				users.add(new Usuario(resultado.getInt(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5),resultado.getString(6),resultado.getString(7),resultado.getString(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static String haySesionParaHoy(Connection connection,Date fechaIntervencion) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select * from Sesion_VE where intervention_sesion='"+fechaIntervencion+"';");
			if(resultado.next()) {
				return  resultado.getString(1);
			}else {
				return "";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} 
		
	}
	public int addQuorum(Connection connection) {
		String sql = "update Sesion_VE set id_quorum =? where convocatoria_sesion=?;";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, id_quorum);
			instruccion.setString(2, convocatoria);
			
			instruccion.execute();
			
			return 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		
	}
	
	
}
