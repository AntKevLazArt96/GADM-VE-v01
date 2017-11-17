package gad.manta.common;

import java.io.Serializable;

public class Documentacion implements Serializable {

	private static final long serialVersionUID = -2406076527285660076L;
	private int punto;
	private String nombre;
	private byte[] pdf;
	public Documentacion() {
		// TODO Auto-generated constructor stub
		
	}
	public Documentacion(int punto, String nombre, byte[] pdf) {
		this.punto = punto;
		this.nombre = nombre;
		this.pdf = pdf;
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
