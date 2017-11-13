package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Client {
    String name;
    String status;
    private DataOutputStream dos;

    public DataOutputStream getDos() {
        return dos;
    }



    Client(String name, DataOutputStream dos, DataInputStream dis) {
        this.name = name;
        this.dos = dos;

        new Thread(() -> {
            try {
                while(true) {
                	status = dis.readUTF();
                    System.out.println(status);
                    List<Client> entry = Server.clients;
                    for (Client cli : entry) {
                        DataOutputStream edos = cli.getDos();
                        edos.writeUTF(status);
                    }
                }
            } catch (IOException E) {
                try {
                    dis.close();
                    dos.close();
                    Server.clients = Server.clients.stream()
                                .filter(e -> {
                                    if(!(e == this)) {
                                        String exit_message = "{ \"name\" : \"" + name + "\", \"status\" : \"DESCONECTADO" + "\"}";;
                                        System.out.println(exit_message);
                                        try {
                                            e.getDos().writeUTF(exit_message);
                                        } catch (IOException err) {
                                            err.printStackTrace();
                                        }
                                    }
                                    return !(e == this);
                                    })
                                .collect(Collectors.toList());
                    
                    System.out.println("[Current User : " + Server.clients.size() + "]");
                    for (int i = 0; i < Server.clients.size(); i++) {
    					System.out.println(Server.clients.get(i).name);
    				}  
                } catch(IOException E2) {
                    E2.printStackTrace();
                }
            }
        }).start();
    }



}
