package cliente;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gad.manta.common.IServidor;
import javafx.fxml.Initializable;

public class ClientePreSesionController implements Initializable  {
	private static IServidor servidor;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			servidor.iniciarSesion();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}

