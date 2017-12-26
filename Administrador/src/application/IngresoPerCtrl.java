package application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import clases.Conexion;
import clases.Imagen;
import clases.Usuarios;
import clases.data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;


public class IngresoPerCtrl implements Initializable {
	//variable para saber de donde proviene
	public static String origen="";
	
    @FXML
    private JFXTextField txt_cedula;

    @FXML
    private JFXTextField txt_nombre;

    @FXML
    private JFXTextField txt_username;

    @FXML
    private JFXPasswordField txt_password;


    @FXML
    private JFXButton btn_examinar;

    @FXML
    private ImageView lbl_foto;

    @FXML
    private JFXButton btn_guardar;
    @FXML
    private JFXButton btn_eliminar;
    
    @FXML
    private JFXComboBox<String> cbx_cargo;
    ObservableList<String> tipo = FXCollections.observableArrayList("Administrador", "Concejal Principal", "Concejal Alterno", "Secretaria");
    
    @FXML
    public AnchorPane panel;
    
    private Conexion conexion;

    int id_img=0;
    @FXML
    void onBuscarFoto(ActionEvent event) {
    	try {
        	FileChooser fc = new FileChooser();
        	fc.setInitialDirectory(new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\res"));
        	fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files","*.png"));
        	File selectedf = fc.showOpenDialog(null);
        	if(selectedf!=null) {
        		String path = selectedf.getAbsolutePath().toString();
        		String file = path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf('.'));
        		String exten = path.substring(path.lastIndexOf('.')+1);
        		System.out.println(file+((int) (Math.random() * 10000) + 50)+"."+exten);
        		String ruta=file+((int) (Math.random() * 10000) + 50)+"."+exten;
        		
        		conexion.establecerConexion();
        		Imagen img = new Imagen(ruta,selectedf.getAbsolutePath().toString());        		
        		id_img = img.guardarRegistro(conexion.getConnection());
        		
        		InputStream is = img.consultarImg(id_img);
    			
        		Image imgn = new Image(is);
    			lbl_foto.setImage(imgn);
    			conexion.cerrarConexion();
    			btn_eliminar.setVisible(true);
    			btn_examinar.setVisible(false);
        	}
		} catch (NullPointerException nl) {
			nl.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    private void limpiar() {
    	txt_cedula.setText("");
    	txt_nombre.setText("");
    	txt_username.setText("");
    	txt_password.setText("");
    	cbx_cargo.setValue("");
    	id_img=0;
    }
    
    @FXML
    void onGuardar(ActionEvent event) throws SQLException {
    	
    	
		conexion.establecerConexion();
		// conecciï¿½n a la base de datos
		Connection db = conexion.getConnection();
		Statement st = db.createStatement();
		ResultSet resultado1 = st.executeQuery("select * from consulta_usuario_ced('" + txt_cedula.getText() + "');");
		db.close();
		
		conexion.establecerConexion();
		Connection db1 = conexion.getConnection();
		Statement st2 = db1.createStatement();
		ResultSet resultado2 = st2.executeQuery("select * from consulta_usuario_user('" + txt_username.getText() + "');");
		db.close();

		
		if (txt_cedula.getLength() == 0) {
			txt_cedula.requestFocus();
			mostrarMesaje("Falta ingresar el número de cédula");

		} else if (resultado1.next()) {
			txt_cedula.requestFocus();
			mostrarMesaje("El usuario con cédula " + txt_cedula.getText() + " ya está registrado en el sistema");

		} else if (txt_nombre.getLength() == 0) {
			txt_nombre.requestFocus();
			mostrarMesaje("Falta ingresar los nombres y los apellidos");
		} else if (txt_username.getLength() == 0) {
			txt_username.requestFocus();
			mostrarMesaje("Falta ingresar el nombre de usuario");
		} else if (resultado2.next()) {
			txt_username.requestFocus();
			mostrarMesaje("La persona con el username " + txt_username.getText() + " ya está registrada en el sistema");

		} else if (txt_password.getLength() == 0) {
			txt_password.requestFocus();
			mostrarMesaje("Falta ingresar la contraceÃ±a del usuario");
		} else if (cbx_cargo.getValue() == null) {
			cbx_cargo.requestFocus();
			//txt_cargo.requestFocus();
			mostrarMesaje("Falta ingresar el cargo del usuario");
		} else if (id_img == 0) {
			btn_examinar.requestFocus();
			mostrarMesaje("Falta ingresar la foto del usuario");
		} else {
			System.out.println(cbx_cargo.getValue());
			Usuarios user = new Usuarios(txt_cedula.getText(), cbx_cargo.getValue(), txt_nombre.getText(),
					txt_username.getText(), txt_password.getText(), id_img);
			int resultado = user.guardarRegistro(conexion.getConnection());

			if (resultado == 1) {
				limpiar();
				imgBlanco();
				mostrarMesaje("El usuario " + txt_nombre.getText() + " a sido ingresado correctamente");
			} else {
				mostrarMesaje("El usuario " + txt_nombre.getText() + " no se a podido agregar");
			}

		}

		conexion.cerrarConexion();
		
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*txt_nombre.setText("AB. JOSï¿½ PICO ARTEAGA");
		txt_cedula.setText("123456789");
		txt_cargo.setText("concejal");
		txt_username.setText("concejal1");
		txt_password.setText("1234");*/
		cbx_cargo.setItems(tipo);
		conexion = new Conexion();
		
		btn_eliminar.setText("");
		btn_eliminar.setVisible(false);
		
	}
	
	private void imgBlanco() {
		btn_eliminar.setVisible(false);
		btn_examinar.setVisible(true);
		InputStream is = new InputStream() {
			
			@Override
			public int read() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		id_img=0;
		lbl_foto.setImage(new Image(is));
	}
	
	@FXML
    void eliminarImg(ActionEvent event) {
		conexion.establecerConexion();
		Imagen img = new Imagen();
		int resultado = img.eliminarImg(id_img,conexion.getConnection());
		conexion.cerrarConexion();
		if(resultado ==1) {
			imgBlanco();
		}else {
			System.out.println("Error en la consulta");
		}
	}
	@FXML
	void  validar_cedula(KeyEvent e) {
		
		char car = e.getCharacter().charAt(0);
		if(!Character.isDigit(car)) {
			e.consume();
		}
		if(txt_cedula.getLength()==10) {
			e.consume();
		}
			
	}
	
	@FXML
	void  validar_nombre(KeyEvent e) {
		
		char car = e.getCharacter().charAt(0);
		if(!(Character.isDigit(car)|| Character.isLetter(car)|| Character.isSpaceChar(car))) {
			e.consume();
		}
		if(txt_nombre.getLength()==100) {
			e.consume();
		}
		
	}
	
	@FXML
	void  validar_usuario(KeyEvent e) {
		
		char car = e.getCharacter().charAt(0);
		if(!(Character.isDigit(car)|| Character.isLetter(car))) {
			e.consume();
		}
		if(txt_username.getLength()==30) {
			e.consume();
		}
		
		
	}
	
	@FXML
	void  validar_contracena(KeyEvent e) {
		
		char car = e.getCharacter().charAt(0);
		if(!(Character.isDigit(car) || Character.isLetter(car))) {
			e.consume();
		}
		if(txt_password.getLength()==30) {
			e.consume();
		}
		
		
	}
	
	void  validar_foto() {
		
		if(id_img !=0) {
			System.out.println("flata foto");
		}
		
		
	}
	
	
	@FXML
	void  cerrar(ActionEvent e) {
		try {
			if(origen.equals("inicio")) {
				AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("subInicio.fxml"));
				panel.getChildren().setAll(pane);
			}else if(origen.equals("usuario")) {
				AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("Usuarios.fxml"));
				panel.getChildren().setAll(pane);
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
