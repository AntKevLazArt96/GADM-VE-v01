package gad.manta.common;

import java.io.Serializable;
import java.sql.Date;

public class Sesion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	

	
	public Sesion(String convocatoria, String titulo) {
		this.convocatoria = convocatoria;
		this.titulo = titulo;
	}
	
}
