package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class OrdenDia {
	private int id;
	private int idSesion;
	private int numeroPunto;
	private String tema;
	private String documentacion;
	private int proponente;
	
	public int getNumeroPunto() {
		return numeroPunto;
	}
	public void setNumeroPunto(int numeroPunto) {
		this.numeroPunto = numeroPunto;
	}
	public int getIdSesion() {
		return idSesion;
	}
	public void setIdSesion(int idSesion) {
		this.idSesion = idSesion;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getDocumentacion() {
		return documentacion;
	}
	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}
	
	public int getProponente() {
		return proponente;
	}
	public void setProponente(int proponente) {
		this.proponente = proponente;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public OrdenDia(int idSesion, int numeroPunto, String tema , String documentacion,int proponente) {
		this.idSesion = idSesion;
		this.numeroPunto= numeroPunto;
		this.tema= tema;
		this.documentacion = documentacion;
		this.proponente = proponente;
	}
	
	public OrdenDia(int id,int idSesion, int numeroPunto, String tema , String documentacion,int proponente) {
		this.id= id;
		this.idSesion = idSesion;
		this.numeroPunto= numeroPunto;
		this.tema= tema;
		this.documentacion = documentacion;
		this.proponente = proponente;
	}
	
	public int guardarRegistro(Connection connection) {
		String sql = "INSERT INTO orden_dia(id_sesion,numero_punto, tema_punto, documentacion,proponente) values (?,?,?,?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, idSesion);
			instruccion.setInt(2, numeroPunto);
			instruccion.setString(3, tema);
			instruccion.setString(4, documentacion);
			instruccion.setInt(5, proponente);
			return instruccion.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	public static void llenarInformacion(Connection connection, ObservableList<OrdenDia> orden, int idSesion) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select *from orden_dia where id_sesion="+idSesion);
			while(resultado.next()) {
				orden.add(new OrdenDia(resultado.getInt(1),resultado.getInt(2),resultado.getInt(3),resultado.getString(4),resultado.getString(5),resultado.getInt(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	


}
