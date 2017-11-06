package gad.manta.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServidor extends Remote {
	public String saludar(String nombre) throws RemoteException;
	public String login(String username,String password) throws RemoteException;
	public int agregarSesion(String fechaRegistro,String fechaIntervencion,String horaIntervencion,String convocatoria,String titulo) throws RemoteException;

}
