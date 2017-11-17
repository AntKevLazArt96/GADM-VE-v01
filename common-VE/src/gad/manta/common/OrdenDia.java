package gad.manta.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class OrdenDia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -197902119814967784L;
	private int id;
	private int idSesion;
	private int numeroPunto;
	private String tema;
	private String proponente;
	
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProponente() {
		return proponente;
	}
	public void setProponente(String proponente) {
		this.proponente = proponente;
	}
	public OrdenDia(int numeroPunto, String tema , String proponente) {

		this.numeroPunto= numeroPunto;
		this.tema= tema;
		this.proponente = proponente;
	}
}
