package application;
	

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import clases.data;
import gad.manta.common.data_configuracion;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	static boolean hayConfig;
	public static Registry registry;
	
	public void mostrarMesaje(String tipo,String subtitulo) {

		try {
			data.header = tipo;
			data.cuerpo = subtitulo;
			Stage newStage = new Stage();
			AnchorPane pane;
			pane = (AnchorPane) FXMLLoader.load(getClass().getResource("VentanaDialogo.fxml"));
			Scene scene = new Scene(pane);
			newStage.setScene(scene);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void start(Stage primaryStage) throws IOException {
		ConfiguracionIniCtrl.cargar();
		if (ConfiguracionIniCtrl.cargar()) {
			data_configuracion.ipBaseDatos=ConfiguracionIniCtrl.config.get(0);
			data_configuracion.puerto_postgres=Integer.valueOf(ConfiguracionIniCtrl.config.get(1));
			data_configuracion.usu_db_postgres=ConfiguracionIniCtrl.config.get(2);
			data_configuracion.conta_usu_postgres=ConfiguracionIniCtrl.config.get(3);
			data_configuracion.nombre_bd_postgres=ConfiguracionIniCtrl.config.get(4);
		}
		configuraciones();		
		if(hayConfig){
			try {
	    		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
				//Scene scene = new Scene(root,400,400);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.UNDECORATED);
				primaryStage.show();
				
			} catch (RemoteException e) {
				System.out.println(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			ConfiguracionIniCtrl.isPanel=false;
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ConfiguracionIni.fxml"));
			//Scene scene = new Scene(root,400,400);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
			mostrarMesaje("No se pudo conectar con el servidor de base de datos deseado","por favor cambie las configuraciones para poder inicar a app");
		}
		//ejecutar socket al iniciar la App
		/*try {
			
			if(Runtime.getRuntime()!=null) {
				System.out.println("Ya se esta ejecuntado");
			}else {
				Runtime.getRuntime().exec("cmd /c java -jar C:\\Socket.jar start");
				System.out.println(Runtime.getRuntime().toString());
			}
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
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