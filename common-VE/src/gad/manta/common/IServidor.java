package gad.manta.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface IServidor extends Remote {
	public String login(String username,String password) throws RemoteException;
	public int agregarSesion(String fechaRegistro,String fechaIntervencion,String horaIntervencion,String convocatoria,String titulo) throws RemoteException;
	public List<String> consultaUsuario()throws RemoteException;
	
	public Usuario usuario(String username)throws RemoteException;
	public List<Usuario> listaUsuarios()throws RemoteException;
	
	public List<Usuario> asistenciaUsuarios(int id_quorum)throws RemoteException;
	
	public List<Usuario> consultaQuorum()throws RemoteException;
	//para crear el voto
	public String addVoto(String usuario, String voto)throws RemoteException;
	//para la lista de votos en la secretaria
	public List<Voto> votantes()throws RemoteException;
	public List<Voto> votosAprobados()throws RemoteException;
	public List<Voto> votosRechazados()throws RemoteException;
	
	
	public Sesion consultarSesion() throws RemoteException;
	public List<OrdenDia> consultarOrden() throws RemoteException;
	public List<Documentacion> mostrarDocumentacion() throws RemoteException;
	public List<Sesion> consultarSesion_Modificacion(String convocatoria) throws RemoteException;
	List<OrdenDia> consultarOrden_Modificacion(String convocatoria) throws RemoteException;
	
	List<OrdenDia> consultarPunto_Modificacion(int id_punto) throws RemoteException;
	List<Pdf> consultarPDFS_Modificacion(int id_punto) throws RemoteException;
}
