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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import gad.manta.common.IServidor;
import gad.manta.common.OrdenDia;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ClienteSesionCtrl implements Initializable {
	
	private static IServidor servidor;
    @FXML
    private JFXButton btn_voz;

    @FXML
    private Label label_convocatoria;
    @FXML
    private Label lbl_punto;
    @FXML
    private Circle cirlogin;

    @FXML
    private Label lbl_nombre;
    
    @FXML
    private JFXTextArea label_titulo;

    @FXML
    private Label label_proponente;

    @SuppressWarnings("rawtypes")
	@FXML
    private TableView table_documentacion;
    
    public static Thread th;
    Socket sock;
    DataOutputStream dos;
    DataInputStream dis;
    
  //inicializamos socket
    public ClienteSesionCtrl() {
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
                       
                        Object obj = parser.parse(newMsgJson);
                        JSONObject msg = (JSONObject) obj;
                        
                        
                        String punto =(String) msg.get("punto");
                        String titulo = (String) msg.get("titulo");
                        String proponente = (String) msg.get("proponente");
                        //String documentacion = (String) msg.get("documento");
                        
                        
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                            	if(punto!=null) {
                            		lbl_punto.setText(""+punto);
                                	label_titulo.setText(titulo);
                                    label_proponente.setText(proponente);
                            	}else {
                            		lbl_punto.setText("Esperando...");
                                	label_titulo.setText("Esperando...");
                                    label_proponente.setText("Esperando...");
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
	public void initialize(URL location, ResourceBundle resources) {
		lbl_nombre.setText(data.name);
		cirlogin.setFill(new ImagePattern(data.Imagen));
        cirlogin.setStroke(Color.SEAGREEN);
        cirlogin.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKGREEN));
		label_convocatoria.setText(data.convocatoria);
	}

}
