package gad.manta.common;

import java.io.Serializable;

public class Mensaje implements Serializable{
	
	
	private static final long serialVersionUID = -6305171183827214226L;
	private String cuerpo, remitente;
	
	public Mensaje(String cuerpo, String remitente) {
        this.cuerpo = cuerpo;
        this.remitente = remitente;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public String getRemitente() {
        return remitente;
    }

}
