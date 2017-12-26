package clases;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Usuarios {
	private int id;
	private String nombre;
	private byte[] img;
	private String cedula;
	private String cargo;
	private String usuario;
	private String password;
	private int id_img;
	private String codigoHuella;
	private int isLooged;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId_img() {
		return id_img;
	}

	public void setId_img(int id_img) {
		this.id_img = id_img;
	}

	public String getCodigoHuella() {
		return codigoHuella;
	}

	public void setCodigoHuella(String codigoHuella) {
		this.codigoHuella = codigoHuella;
	}

	public Usuarios(int id, String nombre, byte[] img) {
		this.id = id;
		this.nombre = nombre;
		this.img = img;
	}

	public Usuarios(int id, String nombre, byte[] img, String Tipo,int logged) {
		this.id = id;
		this.nombre = nombre;
		this.img = img;
		this.cargo = Tipo;
		this.isLooged=logged;
	}

	// constructor para insertar usuarios
	public Usuarios(String cedula, String cargo, String nombre, String username, String pass, int idImg) {
		this.cedula = cedula;
		this.cargo = cargo;
		this.nombre = nombre;
		this.usuario = username;
		this.password = pass;
		this.id_img = idImg;
	}

	// para modificar usuarios
	public Usuarios(String cedula, String cargo, String nombre, String usuario, String password, int id_img,
			String codigoHuella) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.cargo = cargo;
		this.usuario = usuario;
		this.password = password;
		this.id_img = id_img;
		this.codigoHuella = codigoHuella;
	}

	// para consulta de todos los registros
	public Usuarios(int id, String cedula, String cargo, String nombre, String username) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.cedula = cedula;
		this.cargo = cargo;
		this.nombre = nombre;
		this.usuario = username;
	}

	// para consulta de usuarios por cedula
	public Usuarios(int id, String cedula, String cargo, String nombre, String username, String pass, int id_img) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.cedula = cedula;
		this.cargo = cargo;
		this.nombre = nombre;
		this.usuario = username;
		this.password = pass;
		this.id_img = id_img;
	}

	public static Usuarios login(Connection connection, String username, String password) throws RemoteException {
		try {
			Statement st = connection.createStatement();
			int id = 0;
			String usuario = "no hay datos";
			byte[] img = null;
			String tipo_user = "";
			int logged =0;
			Usuarios user = null;

			ResultSet resultado = st
					.executeQuery("select *from verificar_usuario('" + username + "','" + password + "');");
			while (resultado.next()) {
				id = resultado.getInt(1);
				usuario = resultado.getString(2);
				img = resultado.getBytes(3);
				tipo_user = resultado.getString(4);
				logged = resultado.getInt(5);
			}
			System.out.println(usuario);
			if (usuario.equals("no hay datos")) {
				return user;
			} else {
				user = new Usuarios(id, usuario, img, tipo_user,logged);
				
				return user;
				
			}
			

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	public static Usuarios obtener_img(Connection connection, String username) throws RemoteException {

		try {
			Statement st = connection.createStatement(); // ejecucion y resultado de la consulta

			int id;
			String usuario = "";
			byte[] img = null;
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from obtener_img('" + username + "');");
			resultado.next();
			id = resultado.getInt(1);
			usuario = resultado.getString(2);
			img = resultado.getBytes(3);
			connection.close();
			Usuarios user = new Usuarios(id, usuario, img);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	public int guardarRegistro(Connection connection) {
		String sql = "select ingresar_usuario(?,?,?,?,?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setString(1, getCedula());
			instruccion.setString(2, getCargo());
			instruccion.setString(3, getNombre());
			instruccion.setString(4, getUsuario());
			instruccion.setString(5, getPassword());
			instruccion.setInt(6, getId_img());
			instruccion.execute();
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizarRegistro(Connection connection) {
		String sql = "UPDATE user_ve SET cedula_user=?, position_user=?, name_user=?, username_user=?, password_user=?, id_img=?, cod_huella=? WHERE cedula_user='"
				+ getCedula() + "';";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setString(1, getCedula());
			instruccion.setString(2, getCargo());
			instruccion.setString(3, getNombre());
			instruccion.setString(4, getUsuario());
			instruccion.setString(5, getPassword());
			instruccion.setInt(6, getId_img());
			instruccion.setString(7, getCodigoHuella());
			instruccion.execute();
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static void consultaUsuario(Connection connection, List<Usuarios> users) {

		try {
			Statement st = connection.createStatement(); // ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from User_VE");
			while (resultado.next()) {
				users.add(new Usuarios(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getString(5)));
			}
			connection.close();

		} catch (SQLException e) {
			// TODO: handle exception
			e.getMessage();
		}

	}

	public static Usuarios consultaUsuarioCed(Connection connection, String cedula) {
		Usuarios user = null;
		try {
			Statement st = connection.createStatement(); // ejecucion y resultado de la consulta
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from consulta_usuario_ced('" + cedula + "');");
			while (resultado.next()) {
				user = new Usuarios(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getInt(7));
			}
			connection.close();
			return user;
		} catch (SQLException e) {
			// TODO: handle exception
			e.getMessage();
			return user;
		}

	}

	public int getIsLooged() {
		return isLooged;
	}

	public void setIsLooged(int isLooged) {
		this.isLooged = isLooged;
	}

}
