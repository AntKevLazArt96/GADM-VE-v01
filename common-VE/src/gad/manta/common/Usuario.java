package gad.manta.common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Usuario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1317952102785898032L;
	private String nombre,status,img;

	public Usuario(String nombre,String status,String img) {
		this.nombre= nombre;
		this.status = status;
		this.img= img;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
