package gad.manta.common;

import java.io.Serializable;

public class Config implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7741895284757695989L;
	private String ip_s;
	private int port_s;
	public int getPort_s() {
		return port_s;
	}
	public void setport_s(int port_s) {
		this.port_s = port_s;
	}
	public String getIp_s() {
		return ip_s;
	}
	public void setIp_s(String ip_s) {
		this.ip_s = ip_s;
	}
	
	public Config(String ip_s,int port_s) {
		this.ip_s=ip_s;
		this.port_s=port_s;
	}
	
}
