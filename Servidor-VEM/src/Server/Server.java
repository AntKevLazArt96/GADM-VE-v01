package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static List<Client> clients;
    public static DataOutputStream dos;
    DataInputStream dis;
    public ServerSocket servSock;
    
    Server() {
    	System.out.println("Server in the 192.168.1.6:6666 " );
        
    	String name;
        Socket client;
        clients = new ArrayList<Client>();

        try {
            servSock = new ServerSocket(6666);

            while(true) {
                client = servSock.accept();
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());

                name = dis.readUTF() ;
                Client user = new Client(name, dos, dis);
                System.out.println("Connected : " + name);
                
                clients.add(user);
                
     
                
                String enter_message = "{ \"name\" : \"" + name + "\", \"status\" : \"PRESENTE" + "\"}";
                System.out.println(enter_message);
                List<Client> entry = clients;
                for (Client cli : entry) {
                    DataOutputStream edos = cli.getDos();
                    edos.writeUTF(enter_message);
                }

                System.out.println("[Current User : " + Server.clients.size() + "]");
                for (int i = 0; i < clients.size(); i++) {
					System.out.println(clients.get(i).name);
				}  
            }
        } catch(IOException E) {
            E.printStackTrace();
        }
    }
}
