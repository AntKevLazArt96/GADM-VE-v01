package gad.manta.common;

import java.io.Serializable;

public class Mensaje implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2067253262843587817L;
	private String cuerpo, remitente;

	public Mensaje(String cuerpo,String remitente) {
		this.cuerpo=cuerpo;
		this.remitente=remitente;
	}

	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	
}
