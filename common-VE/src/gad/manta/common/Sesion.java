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
	private Date fechaRegistro;
	private Date fechaIntervencion;
	private String horaIntervencion;
	private int id_quorum;
	private int id_pdf;
	private String titulo;
	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	
	
	
}
