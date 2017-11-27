package application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import gad.manta.common.Conexion;
import gad.manta.common.Imagen;
import gad.manta.common.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


public class IngresoPerCtrl implements Initializable {

    @FXML
    private JFXTextField txt_cedula;

    @FXML
    private JFXTextField txt_nombre;

    @FXML
    private JFXTextField txt_username;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private JFXTextField txt_cargo;

    @FXML
    private JFXButton btn_examinar;

    @FXML
    private ImageView lbl_foto;

    @FXML
    private JFXButton btn_guardar;
    @FXML
    private JFXButton btn_eliminar;

    
    
    private Conexion conexion;

    int id_img=0;
    @FXML
    void onBuscarFoto(ActionEvent event) {
    	try {
        	FileChooser fc = new FileChooser();
        	//fc.setInitialDirectory(new File("C:\\GIT\\GADM-VE-v01\\Servidor-VEM\\res"));
        	fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files","*.png"));
        	File selectedf = fc.showOpenDialog(null);
        	if(selectedf!=null) {
        		String path = selectedf.getAbsolutePath().toString();
        		String file = path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf('.'));
        		String exten = path.substring(path.lastIndexOf('.')+1);
        		System.out.println(file+((int) (Math.random() * 10000) + 50)+"."+exten);
        		String ruta=file+((int) (Math.random() * 10000) + 50)+"."+exten;
        		
        		conexion.establecerConexion();
        		/*Imagen img = new Imagen(ruta,selectedf.getAbsolutePath().toString());        		
        		id_img = img.guardarRegistro(conexion.getConnection());
        		
        		InputStream is = img.consultarImg(id_img,conexion.getConnection());
    			
        		Image imgn = new Image(is);
    			lbl_foto.setImage(imgn);*/
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
    
    /*private void limpiar() {
    	txt_cedula.setText("");
    	txt_cargo.setText("");
    	txt_nombre.setText("");
    	txt_username.setText("");
    	txt_password.setText("");
    	id_img=0;
    }*/
    
    @FXML
    void onGuardar(ActionEvent event) {
    	
    	
    	try {
    		conexion.establecerConexion();
    		Usuario user = new Usuario(txt_cedula.getText(),txt_cargo.getText(),txt_nombre.getText(),txt_username.getText(),txt_password.getText(),id_img);
    		int resultado = user.guardarRegistro(conexion.getConnection());
    		if(resultado ==1) {
    			//limpiar();
    			imgBlanco();
    		}else{
    			System.out.println("hay ocurrido algun error");
    		}
    		
    		conexion.cerrarConexion();
    		
    		
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txt_nombre.setText("AB. JOS� PICO ARTEAGA");
		txt_cedula.setText("123456789");
		txt_cargo.setText("concejal");
		txt_username.setText("concejal1");
		txt_password.setText("1234");
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
		lbl_foto.setImage(new Image(is));
	}
	
	@FXML
    void eliminarImg(ActionEvent event) {
		conexion.establecerConexion();
		/*Imagen img = new Imagen();
		int resultado = img.eliminarImg(id_img,conexion.getConnection());
		conexion.cerrarConexion();
		if(resultado ==1) {
			imgBlanco();
		}else {
			System.out.println("Error en la consulta");
		}*/
	}

}
