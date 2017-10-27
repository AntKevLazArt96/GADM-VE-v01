package application;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import gad.manta.common.IServidor;
import gad.manta.common.Utils;
import javafx.fxml.Initializable;
import servidor.Servidor;

public class SampleController implements Initializable{

	public static void inicioServidor() throws IOException, NotBoundException {
		try {
			Utils.setCodeBase(IServidor.class);
			
			Servidor servidor = new Servidor();
			IServidor remote = (IServidor)UnicastRemoteObject.exportObject(servidor, 8888);
			
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("VotoE", remote);
			
			System.out.println(registry.toString());
			System.out.println("Servidor Liso, Preione enter para terminar");
	        System.in.read();
	        
	        registry.unbind("VotoE");
	        UnicastRemoteObject.unexportObject(servidor, true);
			
		} catch (RemoteException e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			inicioServidor();
		} catch (IOException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
