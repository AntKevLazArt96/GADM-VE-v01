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
	//para crear la votacion del orden del dia propuesto(Acta)
	public String addVoto(String usuario, String voto,byte[] img)throws RemoteException;
	//para la lista de votos del orden del dia propuesto
	public List<Voto> votantes()throws RemoteException;
	public List<Voto> votosAprobados()throws RemoteException;
	public List<Voto> votosRechazados()throws RemoteException;
	
	//para crear la votacion de los puntos del orden del dia(punto) 
	public String addVotoPunto(String usuario, String voto,byte[] img)throws RemoteException;
	//para la lista de votos del orden del dia propuesto
	public List<Voto> votantesPunto()throws RemoteException;
	public List<Voto> votosAFavor()throws RemoteException;
	public List<Voto> votosEnContra()throws RemoteException;
	public List<Voto> votosSalvados()throws RemoteException;
	public List<Voto> votosBlanco()throws RemoteException;
	
	
	public Sesion consultarSesion() throws RemoteException;
	public List<OrdenDia> consultarOrden() throws RemoteException;
	public List<Documentacion> mostrarDocumentacion() throws RemoteException;
}
