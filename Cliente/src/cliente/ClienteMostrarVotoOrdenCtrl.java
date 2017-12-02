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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import gad.manta.common.IServidor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClienteMostrarVotoOrdenCtrl implements Initializable {
	volatile boolean ejecutar =true;
	@FXML
    private Label label_convocatoria;
	
	@FXML
    private Label label_convocatoria2;
	@FXML
    private JFXTextArea label_titulo;
	
    @FXML
    private Circle cirlogin;

    @FXML
    private Label lbl_nombre;
	
    @FXML
    private JFXButton verVoto;
    
  //variable statica para rmi
  	static IServidor servidor;
  	
  	//variables estaticas para socket
  	public static Thread th;
      Socket sock;
      DataOutputStream dos;
      DataInputStream dis;
    
    public ClienteMostrarVotoOrdenCtrl() {
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

                    while(ejecutar) {
                        String newMsgJson = dis.readUTF();

                        System.out.println("RE : " + newMsgJson);
                        Mensage newMsg = new Mensage();

                        Object obj = parser.parse(newMsgJson);
                        JSONObject msg = (JSONObject) obj;

                        newMsg.setName((String) msg.get("name"));
                        newMsg.setMessage((String) msg.get("message"));
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                            	if(newMsg.getName()!=null&&newMsg.getName().contains("APROBADO")) {
                            		Alert mensaje = new Alert(AlertType.INFORMATION);
                            		mensaje.setTitle("Orden del d�a Aprobado");
                            		mensaje.setContentText("LA orden del d�a fue aprobada ahora se procedera a mostrar ");
                            		mensaje.setHeaderText("Orden del d�a Aprobado");
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
                                    ejecutar=false;
                            	}
                            	if(newMsg.getName()!= null && newMsg.getName().contains("REINICIAR")) {
                            		Stage stage = (Stage) verVoto.getScene().getWindow();
								    // do what you have to do
								    stage.close();
                            		
                            		Stage newStage = new Stage();
                            		
                            	    AnchorPane pane = null;
									try {
										pane = (AnchorPane)FXMLLoader.load(getClass().getResource("ClienteVotoOrden.fxml"));
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
                                    ejecutar=false;
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
		//cargar header
		
		lbl_nombre.setText(data.name);
		
		
		cirlogin.setFill(new ImagePattern(data.Imagen));
        cirlogin.setStroke(Color.SEAGREEN);
        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
        
		label_titulo.setText(data.titulo);
		label_convocatoria.setText(data.convocatoria);
		label_convocatoria2.setText(data.convocatoria);
		
		
		
		if(data.voto.equals("APROBADO")) {
			verVoto.setText("APROBADO");	
		}
		if(data.voto.equals("RECHAZADO")) {
			verVoto.setText("RECHAZADO");	
			verVoto.setStyle("-fx-background-color: red;");
		}
		
		try {
			servidor = (IServidor)Naming.lookup("rmi://192.168.1.6/VotoE");
		} catch (MalformedURLException | RemoteException | NotBoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
	
		
		
		
		
		
	}

}
