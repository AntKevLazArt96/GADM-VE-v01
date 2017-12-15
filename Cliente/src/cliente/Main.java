package cliente;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	public static List<String> config = new ArrayList<>();
	@Override
	public void start(Stage primaryStage) {
		try {

			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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

	public static void main(String[] args) {

		launch(args);
	}
}
