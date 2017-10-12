package gad.manta.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServidor extends Remote {
	public int autenticar(String nombre) throws RemoteException;
    public int agregar(String nombre, int sesion) throws RemoteException;
    public void enviar(String cuerpo, int sesionDe, int SesionA)throws RemoteException;
    public List<Mensaje> recibir(int sesion)throws RemoteException;
    public void LimpiarBuffer(int sesion) throws RemoteException;
}
