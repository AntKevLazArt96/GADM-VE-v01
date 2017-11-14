package gad.manta.common;

import java.io.Serializable;

public class Voto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1317952102785898032L;
	private String nombre,voto;

	public Voto(String nombre,String voto) {
		this.setNombre(nombre);
		this.setVoto(voto);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getVoto() {
		return voto;
	}

	public void setVoto(String voto) {
		this.voto = voto;
	}



	
}
