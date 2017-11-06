package application;
	

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import gad.manta.common.IServidor;
import gad.manta.common.Utils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import servidor.Servidor;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
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
			System.out.println("holi");
		}
		
		try {
			
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			//Scene scene = new Scene(root,400,400);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
