package gad.manta.common;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1317952102785898032L;
	private int id;
	private String nombre, status;
	private byte[] img;
	private String cedula;
	private String cargo;
	private String usuario;
	private String password;
	private int id_img;
	private String codigoHuella;

	public Usuario() {
		super();
	}

	public Usuario(int id, String nombre, String status, byte[] img) {
		this.id = id;
		this.nombre = nombre;
		this.status = status;
		this.img = img;
	}

	public Usuario(Integer id, String cedula, String cargo, String nombre, String usuario, String password,
			String ruta_img, String codigoHuella) {
		this.id = id;
		this.cedula = cedula;
		this.nombre = nombre;
		this.cargo = cargo;
		this.usuario = usuario;
		this.password = password;
		this.codigoHuella = codigoHuella;
	}

	public Usuario(String cedula, String cargo, String nombre, String usuario, String password, int id_img) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.cargo = cargo;
		this.usuario = usuario;
		this.password = password;
		this.id_img = id_img;
	}

	public Usuario(String cedula, String cargo, String nombre, String usuario, String password, int id_img,
			String codigoHuella) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.cargo = cargo;
		this.usuario = usuario;
		this.password = password;
		this.id_img = id_img;
		this.codigoHuella = codigoHuella;
	}

	public Usuario(int id, String nombre, byte[] img) {
		this.id = id;
		this.nombre = nombre;
		this.img = img;
	}

	public Usuario(int id, String usuario) {
		this.id = id;
		this.usuario = usuario;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public static void llenarInformacion(Connection connection, ObservableList<Usuario> users) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select *from User_VE");
			while (resultado.next()) {
				users.add(new Usuario(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getString(7),
						resultado.getString(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return nombre;
	}

	public int guardarRegistro(Connection connection) throws IOException {
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

	public int actualizarRegistro(Connection connection) throws IOException {
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

}
