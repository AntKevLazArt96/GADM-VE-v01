package application;
	

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gad.manta.common.IServidor;
import gad.manta.common.Utils;
import gad.manta.common.data_configuracion;
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
			IServidor remote = (IServidor)UnicastRemoteObject.exportObject(servidor, data_configuracion.puerto_rmi);
			
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
		
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("NuevaSesion.fxml"));
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
	
	public static void configuraciones() {
		Connection db;
		try {
			db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
			Statement st = db.createStatement();
			ResultSet resultado= st.executeQuery("select * from configuracion_ve where id_confi=1;");
			resultado.next();
			//rmi
			data_configuracion.ip_rmi=resultado.getString(2);
			data_configuracion.puerto_rmi=resultado.getInt(4);
			
			//bd
			data_configuracion.nombre_bd=resultado.getString(6);
			
			data_configuracion.usu_db=resultado.getString(7);
			
			data_configuracion.conta_usu=resultado.getString(8);
			
			//socket
			data_configuracion.ip=resultado.getString(3);
			data_configuracion.port=resultado.getInt(5);
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		configuraciones();		
		launch(args);
				
			
				
	}
}
