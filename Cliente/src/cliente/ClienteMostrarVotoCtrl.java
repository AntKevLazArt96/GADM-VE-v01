package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jfoenix.controls.JFXButton;

import gad.manta.common.IServidor;
import gad.manta.common.Voto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClienteMostrarVotoCtrl implements Initializable {

    @FXML
    private JFXButton verVoto;
    
  //variable statica para rmi
  	private static IServidor servidor;
  	
  	//variables estaticas para socket
  	public static Thread th;
      Socket sock;
      DataOutputStream dos;
      DataInputStream dis;
    
    public ClienteMostrarVotoCtrl() {
    	try {
  			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
  		} catch (MalformedURLException | RemoteException | NotBoundException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
    	
        try {

            sock = new Socket(data.ip, data.port);
            dos = new DataOutputStream(sock.getOutputStream());
            dis = new DataInputStream(sock.getInputStream());

            dos.writeUTF(data.name);
            /*
            * This Thread let the client recieve the message from the server for any time;
            */
            th = new Thread(() -> {
                try {

                    JSONParser parser = new JSONParser();

                    while(true) {
                        String newMsgJson = dis.readUTF();

                        System.out.println("RE : " + newMsgJson);
                        Message newMsg = new Message();

                        Object obj = parser.parse(newMsgJson);
                        JSONObject msg = (JSONObject) obj;

                        newMsg.setName((String) msg.get("name"));
                        newMsg.setMessage((String) msg.get("message"));
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                            	if(newMsg.getName().contains("APROBADO")) {
                            		Alert mensaje = new Alert(AlertType.INFORMATION);
                            		mensaje.setTitle("Orden del día Aprobado");
                            		mensaje.setContentText("LA orden del día fue aprobada ahora se procedera a mostrar ");
                            		mensaje.setHeaderText("Orden del día Aprobado");
                            		mensaje.show();	
                            		
                            		System.out.println("estoy en el cliente y se cambio de pantalla en el servidor");
                            		Stage stage = (Stage) verVoto.getScene().getWindow();
								    // do what you have to do
								    stage.close();
                            		
                            		Stage newStage = new Stage();
                            		
                            	    AnchorPane pane = null;
									try {
										pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ClienteSesion.fxml"));
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
                                    Scene scene = new Scene(pane);
                                    
                                    //Pantalla completa
                                    Screen screen = Screen.getPrimary();
                            		Rectangle2D bounds = screen.getVisualBounds();

                            		newStage.setX(bounds.getMinX());
                            		newStage.setY(bounds.getMinY());
                            		newStage.setWidth(bounds.getWidth());
                            		newStage.setHeight(bounds.getHeight());
                                    
                                    
                                    newStage.setScene(scene);
                                    newStage.initStyle(StageStyle.UNDECORATED);
                                    newStage.show();
                            		
                            	}
                            	
                            	
                            }
                        });
                                                
                    }
                } catch(Exception E) {
                    E.printStackTrace();
                }

            });

            th.start();

        } catch(IOException E) {
            E.printStackTrace();
        }

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(data.voto.equals("APROBAR")) {
			verVoto.setText("APROBAR");	
		}
		if(data.voto.equals("RECHAZAR")) {
			verVoto.setText("RECHAZAR");	
			verVoto.setStyle("-fx-background-color: red;");
		}
		
		
		
	}

}
