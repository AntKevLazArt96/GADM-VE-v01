package gad.manta.common;

import java.io.Serializable;

import javafx.scene.image.Image;

public class Usuario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1317952102785898032L;
	private int id;
	private String nombre,status;
	private byte[] img;
	
	
	public Usuario(int id, String nombre,String status,byte[] img) {
		this.id=id;
		this.nombre= nombre;
		this.status = status;
		this.img= img;
	}
	
	public Usuario() {
		
	}


	public Usuario(int id, String nombre, byte[] img) {
		this.id=id;
		this.nombre= nombre;
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

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
