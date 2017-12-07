package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfiguracionCtrl implements Initializable {
	 @FXML
	    private JFXTextField txt_nombrebd;
	 @FXML
	    private JFXTextField txt_userdb;
	 @FXML
	    private JFXPasswordField txt_contradb;
	 @FXML
	    private JFXTextField txt_iprmi;
	 @FXML
	    private JFXTextField txt_ipsocket;
	 @FXML
	    private JFXTextField txt_portrmi;
	 @FXML
	    private JFXTextField txt_portsocket;
	 @FXML
	    public AnchorPane panel;

	    @FXML
	    private JFXButton btn_guardar;
	    @FXML
	    private JFXButton btn_modificar;

	 
	public ConfiguracionCtrl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Connection db;
		try {
			db = DriverManager.getConnection("jdbc:postgresql:"+data_configuracion.nombre_bd+"",""+data_configuracion.usu_db+"",""+data_configuracion.conta_usu+"");
			Statement st = db.createStatement();
			ResultSet resultado= st.executeQuery("select * from configuracion_ve where id_confi=1;");
			
			if(resultado.next()) {
				
				txt_iprmi.setText(resultado.getString(2));
				txt_ipsocket.setText(resultado.getString(3));
				txt_portrmi.setText(resultado.getString(4));
				txt_portsocket.setText(resultado.getString(5));
				txt_nombrebd.setText(resultado.getString(6));
				txt_userdb.setText(resultado.getString(7));
				txt_contradb.setText(resultado.getString(8));
				btn_modificar.setDisable(false);
    			
    		}else{
    			mostrarMesaje("Configuracion no encontrada");
    		}
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	 
		@FXML
		void  cerrar(ActionEvent e) {
			try {
				AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("PanelControl.fxml"));
				panel.getChildren().setAll(pane);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	@FXML
		void  modificacion(ActionEvent e) {
				txt_iprmi.setDisable(false);
				txt_ipsocket.setDisable(false);
				txt_portrmi.setDisable(false);
				txt_portsocket.setDisable(false);
				txt_nombrebd.setDisable(false);
				txt_userdb.setDisable(false);
				txt_contradb.setDisable(false);
				btn_guardar.setDisable(false);
				btn_modificar.setDisable(true);

		}
	
	@FXML
	void  guardar(ActionEvent e)throws IOException {
		
		Connection db;
		
		String sql = "UPDATE public.configuracion_ve SET  iprmi_confi=?, ipsocket_confi=?, puertormi_confi=?,puertosocket_confi=?, nombrebd_confi=?, userdb_confi=?, passbd_confi=? WHERE id_confi=1;";
		try {
			db = DriverManager.getConnection("jdbc:postgresql:"+data_configuracion.nombre_bd+"",""+data_configuracion.usu_db+"",""+data_configuracion.conta_usu+"");
			PreparedStatement instruccion = db.prepareStatement(sql);
			instruccion.setString(1, txt_iprmi.getText());
			instruccion.setString(2,txt_ipsocket.getText() );
			instruccion.setInt(3,Integer.parseInt(txt_portrmi.getText() ));
			instruccion.setInt(4,Integer.parseInt( txt_portsocket.getText()));
			instruccion.setString(5,txt_nombrebd.getText());
			instruccion.setString(6, txt_userdb.getText());
			instruccion.setString(7, txt_contradb.getText());
			if(!instruccion.execute()) {
				mostrarMesaje("La configuración a sido atualizada");
				txt_iprmi.setDisable(true);
				txt_ipsocket.setDisable(true);
				txt_portrmi.setDisable(true);
				txt_portsocket.setDisable(true);
				txt_nombrebd.setDisable(true);
				txt_userdb.setDisable(true);
				txt_contradb.setDisable(true);
				btn_guardar.setDisable(true);
				btn_modificar.setDisable(false);
			}else{
				mostrarMesaje("No se a podido actualizar la configuración");
			};
			db.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		

	}
}
