package gad.manta.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServidor extends Remote {
	public String saludar(String nombre) throws RemoteException;
	public String login(String username,String password) throws RemoteException;
	public int agregarSesion(String fechaRegistro,String fechaIntervencion,String horaIntervencion,String convocatoria,String titulo) throws RemoteException;
	public List<String> consultaUsuario()throws RemoteException;
	
	public List<Usuario> consultaQuorum()throws RemoteException;
	
	public int iniciarSesion(String nombre)throws RemoteException;
	public void enviar(String cuerpo, int sesionDe, int SesionA)throws RemoteException;
}
