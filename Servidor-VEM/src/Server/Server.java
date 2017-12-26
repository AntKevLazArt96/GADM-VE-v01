package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import application.Main;
import gad.manta.common.data_configuracion;

public class Server {

	public static List<Client> clients;
	public static DataOutputStream dos;
	DataInputStream dis;
	public ServerSocket servSock;

	public static void configuraciones() {
		Connection db;
		try {
			System.out.println(data_configuracion.ipBaseDatos);
			db = DriverManager
					.getConnection(
							"jdbc:postgresql://" + data_configuracion.ipBaseDatos + "/"
									+ data_configuracion.nombre_bd_postgres + "",
							data_configuracion.usu_db_postgres, data_configuracion.conta_usu_postgres);
			Statement st = db.createStatement();
			ResultSet resultado = st.executeQuery("select * from configuracion_ve where id_confi=1;");
			resultado.next();
			data_configuracion.ip = resultado.getString(3);
			data_configuracion.port = resultado.getInt(5);
			System.out.println(data_configuracion.port);

			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Server() {
		if (Main.cargar()) {
			data_configuracion.ipBaseDatos = Main.config.get(0);
			data_configuracion.puerto_postgres = Integer.valueOf(Main.config.get(1));
			data_configuracion.usu_db_postgres = Main.config.get(2);
			data_configuracion.conta_usu_postgres = Main.config.get(3);
			data_configuracion.nombre_bd_postgres = Main.config.get(4);
		}
		configuraciones();
		System.out.println("Server in the " + data_configuracion.ip + ":" + data_configuracion.port + " ");

		String name;
		Socket client;
		clients = new ArrayList<Client>();

		try {
			servSock = new ServerSocket(6666);

			while (true) {
				client = servSock.accept();
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());

				name = dis.readUTF();
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
		} catch (IOException E) {
			E.printStackTrace();
		}
	}
}
