/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import gad.manta.common.IServidor;
import gad.manta.common.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import servidor.Servidor;

/**
 *
 * @author Usuario
 */
public class InicioController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	try {
			Utils.setCodeBase(IServidor.class);
			
			Servidor servidor = new Servidor();
			IServidor remote = (IServidor)UnicastRemoteObject.exportObject(servidor, 8888);
			
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("VotoE", remote);
			
			System.out.println(registry.toString());
			System.out.println("Servidor Liso, Preione enter para terminar");
	        /*System.in.read();
	    
	        registry.unbind("VotoE");
	        UnicastRemoteObject.unexportObject(servidor, true);
			*/
		} catch (RemoteException e) {
			System.out.println(e);
		}
    }    
    
}
