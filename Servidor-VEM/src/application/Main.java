package application;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	public static List<String> config = new ArrayList<>();
	public static Registry registry;
	static boolean hayConfig;
	public static boolean cargar() {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			File f = new File("C://GIT/GADM-VE-v01/Conexion.xml");
			Document document = builder.parse(f);
			recorrerRama(document);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	private static void recorrerRama(Node nodo) {
		if (nodo != null) {
			if (nodo.getNodeName().equals("#text")) {
				System.out.println("valor del nodo: " + nodo.getNodeValue());
				config.add(nodo.getNodeValue());
			}
			// System.out.println("nombre del nodo: "+nodo.getNodeName());
			// System.out.println("valor del nodo: "+nodo.getNodeValue());
			NodeList hijos = nodo.getChildNodes();
			for (int i = 0; i < hijos.getLength(); i++) {
				Node nodoNieto = hijos.item(i);
				recorrerRama(nodoNieto);
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		if (cargar()) {

			data_configuracion.ipBaseDatos = config.get(0);
			data_configuracion.puerto_postgres = Integer.valueOf(config.get(1));
			data_configuracion.usu_db_postgres = config.get(2);
			data_configuracion.conta_usu_postgres = config.get(3);
			data_configuracion.nombre_bd_postgres = config.get(4);
		}
		configuraciones();
		try {
			Utils.setCodeBase(IServidor.class);
			Servidor servidor = new Servidor();
			IServidor remote = (IServidor) UnicastRemoteObject.exportObject(servidor, data_configuracion.puerto_rmi);
			// System.out.println(data_configuracion.puerto_rmi);
			registry = LocateRegistry.createRegistry(1099);
			registry.rebind("VotoE", remote);
			System.out.println(registry.toString());
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Login.fxml"));
			// Scene scene = new Scene(root,400,400);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();

			/*
			 * System.in.read();
			 * 
			 * registry.unbind("VotoE"); UnicastRemoteObject.unexportObject(servidor, true);
			 */
		} catch (RemoteException e) {
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void configuraciones() {
		Connection db;
		try {
			System.out.println("La ip de la base de datos es "+data_configuracion.ipBaseDatos);
			db = DriverManager.getConnection("jdbc:postgresql://"+data_configuracion.ipBaseDatos+"/"+data_configuracion.nombre_bd_postgres,data_configuracion.usu_db_postgres,data_configuracion.conta_usu_postgres);
			Statement st = db.createStatement();
			ResultSet resultado= st.executeQuery("select * from configuracion_ve where id_confi=1;");
			resultado.next();
			//rmi
			data_configuracion.ip_rmi=resultado.getString(2);
			data_configuracion.puerto_rmi=resultado.getInt(4);
			System.out.println(data_configuracion.ip_rmi);
			
			//bd
			data_configuracion.nombre_bd=resultado.getString(6);
			
			data_configuracion.usu_db=resultado.getString(7);
			
			data_configuracion.conta_usu=resultado.getString(8);
			
			//socket
			data_configuracion.ip=resultado.getString(3);
			data_configuracion.port=resultado.getInt(5);
			db.close();
			hayConfig=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			hayConfig=false;
		}
		
		

	}
	public static void main(String[] args) {
		launch(args);

	}
}
