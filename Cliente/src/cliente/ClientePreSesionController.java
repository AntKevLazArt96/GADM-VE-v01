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
import java.util.ResourceBundle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import gad.manta.common.IServidor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientePreSesionController implements Initializable  {
	@FXML
    private Label lblOrden;
	
	
	//variable statica para rmi
	private static IServidor servidor;
	
	//variables estaticas para socket
	public static Thread th;
    Socket sock;
    DataOutputStream dos;
    DataInputStream dis;
    
    
	
	//inicializamos socket
	 public ClientePreSesionController() {
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
	                        newMsg.setMessage((String) msg.get("status"));
	                        Platform.runLater(new Runnable() {
	                            @Override
	                            public void run() {
	                            	if(newMsg.getName().equals("cambio de pantalla")) {
	                            		System.out.println("estoy en el cliente y se cambio de pantalla en el servidor");
	                            		Stage stage = (Stage) lblOrden.getScene().getWindow();
									    // do what you have to do
									    stage.close();
	                            		
	                            		Stage newStage = new Stage();
	                            		
	                            	    AnchorPane pane = null;
										try {
											pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ClienteVotoAprobacion.fxml"));
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
		
				
	}
	
}

