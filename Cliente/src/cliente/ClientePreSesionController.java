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
import javafx.fxml.Initializable;

public class ClientePreSesionController implements Initializable  {
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
	                        newMsg.setMessage((String) msg.get("message"));
	                        /*if(data.name.equals("secretaria")) {
	                        	 chatLog.appendText(newMsg.getName() + " : " + newMsg.getMessage() + "\n");
	                        }*/
	                                                
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

