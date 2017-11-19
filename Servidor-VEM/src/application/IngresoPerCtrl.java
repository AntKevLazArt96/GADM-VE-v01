package application;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modelo.ActaPdf;
import modelo.Conexion;
import modelo.Usuario;

public class IngresoPerCtrl implements Initializable {

    @FXML
    private JFXTextField txt_cedula;

    @FXML
    private JFXTextField txt_nombre;

    @FXML
    private JFXTextField txt_username;

    @FXML
    private JFXTextField txt_password;

    @FXML
    private JFXTextField txt_cargo;

    @FXML
    private JFXButton btn_examinar;

    @FXML
    private ImageView lbl_foto;

    @FXML
    private JFXButton btn_guardar;
    
    private Conexion conexion;

    @FXML
    void onBuscarFoto(ActionEvent event) {
    	try {
        	FileChooser fc = new FileChooser();
        	//fc.setInitialDirectory(new File(""));
        	fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files","*.png"));
        	File selectedf = fc.showOpenDialog(null);
        	if(selectedf!=null) {
        		selectedf.getAbsolutePath();		
    			conexion.establecerConexion();
    			Usuario user = new Usuario(txt_cedula.getText(),txt_cargo.getText(),txt_nombre.getText(),txt_username.getText(), txt_password.getText(),selectedf.getAbsolutePath().toString());
    			user.guardarRegistro(conexion.getConnection());
    			InputStream is = user.consultarImg(conexion.getConnection());
        		
    			Image img = new Image(is);
    			
    			lbl_foto.setImage(img);
    			
    			conexion.cerrarConexion();
        	}
		} catch (NullPointerException nl) {
			nl.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    @FXML
    void onGuardar(ActionEvent event) {
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		conexion = new Conexion();
		
	}

}
