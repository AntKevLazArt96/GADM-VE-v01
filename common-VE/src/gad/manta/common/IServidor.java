package gad.manta.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServidor extends Remote {
	public String saludar(String nombre) throws RemoteException;

}
