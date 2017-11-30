package gad.manta.common;

import java.io.Serializable;

public class Documentacion implements Serializable {

	private static final long serialVersionUID = -2406076527285660076L;
	private int id_pdf;
	private int punto;
	private String nombre;
	private byte[] pdf;
	public Documentacion() {
		// TODO Auto-generated constructor stub
		
	}
	
	
	public Documentacion(int id_pdf, int punto, String nombre) {
		this.id_pdf = id_pdf;
		this.punto = punto;
		this.nombre = nombre;
	}



	public int getId_pdf() {
		return id_pdf;
	}



	public void setId_pdf(int id_pdf) {
		this.id_pdf = id_pdf;
	}



	public int getPunto() {
		return punto;
	}
	public void setPunto(int punto) {
		this.punto = punto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

}
