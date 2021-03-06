package gad.manta.common;

import java.io.Serializable;

public class Config implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7741895284757695989L;
	private String ip_s;
	private int port_s;
	private String ip_r;
	private int port_r;
	
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
	
	public Config(String ip_s,int port_s,String ip_r, int port_r) {
		this.ip_s=ip_s;
		this.port_s=port_s;
		this.ip_r=ip_r;
		this.port_r=port_r;
	}
	public String getIp_r() {
		return ip_r;
	}
	public void setIp_r(String ip_r) {
		this.ip_r = ip_r;
	}
	public int getPort_r() {
		return port_r;
	}
	public void setPort_r(int port_r) {
		this.port_r = port_r;
	}
	
}
