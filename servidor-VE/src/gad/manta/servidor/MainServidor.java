package gad.manta.servidor;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import gad.manta.common.IServidor;
import gad.manta.common.Utils;

public class MainServidor {
	public static void main(String[] args) throws Exception {
		try {
			Utils.setCodeBase(IServidor.class);
			
			Servidor servidor = new Servidor();
			IServidor remote = (IServidor)UnicastRemoteObject.exportObject(servidor, 8888);
			
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Pepito", remote);
			
			System.out.println(registry.toString());
			System.out.println("Servidor Liso, Preione enter para terminar");
	        System.in.read();
	        
	        registry.unbind("Pepito");
	        UnicastRemoteObject.unexportObject(servidor, true);
			
		} catch (RemoteException e) {
			System.out.println(e);
		}
	}
}
