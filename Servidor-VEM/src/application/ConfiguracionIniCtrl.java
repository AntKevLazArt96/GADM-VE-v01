package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import clases.data;
import gad.manta.common.data_configuracion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class ConfiguracionIniCtrl implements Initializable {
	// Lista donde se guarda la configuracion del Nodo
	public static List<String> config = new ArrayList<>();

	@FXML
	private JFXTextField txt_hostname;

	@FXML
	private JFXTextField txt_puerto;

	@FXML
	private JFXTextField txt_userdb;

	@FXML
	private JFXPasswordField txt_contradb;

	@FXML
	private JFXTextField txt_nombrebd;

	@FXML
	private JFXButton btn_guardar, btn_cerrar, btn_modificar;

	@FXML
	void cerrar(ActionEvent event) {
		// get a handle to the stage
		Stage stage = (Stage) btn_cerrar.getScene().getWindow();
		// do what you have to do
		stage.close();
		System.exit(0);
	}

	@FXML
	void guardar(ActionEvent event)
			throws TransformerConfigurationException, ParserConfigurationException, TransformerException {

		bloquear();
		crearXML2(txt_hostname.getText(), txt_puerto.getText(), txt_userdb.getText(), txt_contradb.getText(),
				txt_nombrebd.getText());
		mostrarMesaje("Para Iniciar la sesion reinicie la aplicacion");
	}
	
	public void mostrarMesaje(String subtitulo) {

		try {
			data.header = "Aviso";
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

	public void crearXML2(String hostname, String port, String user, String password, String db)
			throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();

		Document document = implementation.createDocument(null, "Configuracion", null);
		document.setXmlVersion("1.0");

		Element raiz = document.getDocumentElement();

		Element nodoServidor = document.createElement("HostName"); // creamos un nuevo elemento
		Text nodoValorServidor = document.createTextNode(hostname); // Ingresamos la info
		nodoServidor.appendChild(nodoValorServidor);
		raiz.appendChild(nodoServidor); // pegamos el elemento a la raiz "Documento"

		Element nodoPort = document.createElement("Port"); // creamos un nuevo elemento
		Text nodoValorPort = document.createTextNode(port); // Ingresamos la info
		nodoPort.appendChild(nodoValorPort);
		raiz.appendChild(nodoPort); // pegamos el elemento a la raiz "Documento"

		Element nodoUsuario = document.createElement("User"); // creamos un nuevo elemento
		Text nodoValorUsuario = document.createTextNode(user); // Ingresamos la info
		nodoUsuario.appendChild(nodoValorUsuario);
		raiz.appendChild(nodoUsuario); // pegamos el elemento a la raiz "Documento"

		Element nodoPass = document.createElement("Password"); // creamos un nuevo elemento
		Text nodoValorPass = document.createTextNode(password); // Ingresamos la info
		nodoPass.appendChild(nodoValorPass);
		raiz.appendChild(nodoPass); // pegamos el elemento a la raiz "Documento"

		Element nodoDB = document.createElement("DataBase"); // creamos un nuevo elemento
		Text nodoValorDB = document.createTextNode(db); // Ingresamos la info
		nodoDB.appendChild(nodoValorDB);
		raiz.appendChild(nodoDB); // pegamos el elemento a la raiz "Documento"

		Source source = new DOMSource(document);
		StreamResult result = new StreamResult(new

		File("C://GIT/GADM-VE-v01/Conexion.xml"));// nombre del archivo
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, result);
	}

	@FXML
	void modificacion(ActionEvent event) {
		Desbloquear();
	}

	private void Desbloquear() {
		txt_hostname.setDisable(false);
		txt_puerto.setDisable(false);
		txt_userdb.setDisable(false);
		txt_contradb.setDisable(false);
		txt_nombrebd.setDisable(false);
		btn_guardar.setDisable(false);
		btn_modificar.setVisible(false);
	}

	private void bloquear() {
		txt_hostname.setDisable(true);
		txt_puerto.setDisable(true);
		txt_userdb.setDisable(true);
		txt_contradb.setDisable(true);
		txt_nombrebd.setDisable(true);
		btn_guardar.setDisable(true);
		btn_modificar.setVisible(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (cargar()) {
			txt_hostname.setText(config.get(0));
			txt_puerto.setText(config.get(1));
			txt_userdb.setText(config.get(2));
			txt_contradb.setText(config.get(3));
			txt_nombrebd.setText(config.get(4));
		}

		if (data_configuracion.ipBaseDatos != "") {
			btn_modificar.setDisable(false);

		}
	}

}
