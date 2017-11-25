package gad.manta.common;

import java.io.Serializable;

public class Voto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1317952102785898032L;
	private String nombre,voto;
	private byte[] img;

	public Voto(String nombre,String voto,byte[] img) {
		this.nombre=nombre;
		this.voto=voto;
		this.img=img;
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

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}



	
}
