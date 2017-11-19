package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class OrdenDia {
	private int id;
	private String convocatoria;
	private int numeroPunto;
	private String tema;
	private int proponente;
	
	public int getNumeroPunto() {
		return numeroPunto;
	}
	public void setNumeroPunto(int numeroPunto) {
		this.numeroPunto = numeroPunto;
	}
	
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
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
	
	
	public OrdenDia(String convocatoria, int numeroPunto, String tema ,int proponente) {
		this.convocatoria = convocatoria;
		this.numeroPunto= numeroPunto;
		this.tema= tema;
		this.proponente = proponente;
	}
	
	public OrdenDia(int id,String convocatoria, int numeroPunto, String tema , int proponente) {
		this.id= id;
		this.convocatoria = convocatoria;
		this.numeroPunto= numeroPunto;
		this.tema= tema;
		this.proponente = proponente;
	}
	
	public int guardarRegistro(Connection connection) {
		String sql = "select *from ingresar_orden_dia(?,?,?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setString(1, convocatoria);
			instruccion.setInt(2, numeroPunto);
			instruccion.setString(3, tema);
			instruccion.setInt(4, proponente);
			instruccion.execute();
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select id_ordendia from OrdenDia_VE order by id_ordendia desc limit  1");
			int id = 0;
			resultado.next();
			id=resultado.getInt(1);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	public static void llenarInformacion(Connection connection, ObservableList<OrdenDia> orden, String convocatoria) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select *from OrdenDia_VE where convocatoria_sesion='"+convocatoria+"';");
			while(resultado.next()) {
				orden.add(new OrdenDia(resultado.getInt(1),resultado.getString(2),resultado.getInt(3),resultado.getString(4),resultado.getInt(5)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public String getConvocatoria() {
		return convocatoria;
	}
	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

}
