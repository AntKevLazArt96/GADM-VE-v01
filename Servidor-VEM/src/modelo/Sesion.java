package modelo;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class Sesion {
	private int id;
	private Date fechaRegistro;
	private Date fechaIntervencion;
	private String horaIntervencion;
	private String convocatoria;
	private String titulo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getConvocatoria() {
		return convocatoria;
	}
	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Sesion(Date fechaRegistro, Date fechaIntervencion, String horaIntervencion, String convocatoria,String titulo) {
		this.fechaRegistro = fechaRegistro;
		this.fechaIntervencion = fechaIntervencion;
		this.horaIntervencion = horaIntervencion;
		this.convocatoria = convocatoria;
		this.titulo=titulo;
	}
	
	
	public Sesion(int id, Date fechaRegistro, Date fechaIntervencion, String horaIntervencion, String convocatoria,String titulo) {
		this.id=id;
		this.fechaRegistro = fechaRegistro;
		this.fechaIntervencion = fechaIntervencion;
		this.horaIntervencion = horaIntervencion;
		this.convocatoria = convocatoria;
		this.titulo=titulo;
	}
	
	
	
	public int guardarRegistro(Connection connection) {
		String sql = "insert into sesion(fecha_registro,fecha_intervencion,hora_intervencion,convocatoria,titulo) values (?,?,?,?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setDate(1, fechaRegistro);
			instruccion.setDate(2, fechaIntervencion);
			instruccion.setString(3, horaIntervencion);
			instruccion.setString(4, convocatoria);
			instruccion.setString(5, titulo);
			instruccion.execute();
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select id from sesion where convocatoria='"+convocatoria+"'");
			int id = 0;
			while(resultado.next()) {
				id=resultado.getInt(1);
			}
			
			return id;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public static void llenarInformacion(Connection connection, ObservableList<Usuario> users) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select *from usuario");
			while(resultado.next()) {
				users.add(new Usuario(resultado.getInt(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5),resultado.getString(6),resultado.getString(7),resultado.getString(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static boolean haySesionParaHoy(Connection connection,Date fechaIntervencion) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select * from sesion where fecha_intervencion ='"+fechaIntervencion+"';");
			if(resultado.next()) {
				return  true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		
		
		
	}
	

	
}
