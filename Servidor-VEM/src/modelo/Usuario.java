package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class Usuario {
	private Integer id;
	private String cedula;
	private String nombre;
	private String cargo;
	private String usuario;
	private String password;
	private String telefono;
	private String codigoHuella;
	
	public Usuario(Integer id , String cedula, String nombre, String cargo, String usuario, String password, String telefono, String codigoHuella) {
		this.id=id;
		this.cedula=cedula;
		this.nombre=nombre;
		this.cargo=cargo;
		this.usuario=usuario;
		this.password=password;
		this.telefono=telefono;
		this.codigoHuella=codigoHuella;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
			ResultSet resultado = statement.executeQuery("select *from usuario");
			while(resultado.next()) {
				users.add(new Usuario(resultado.getInt(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),resultado.getString(5),resultado.getString(6),resultado.getString(7),resultado.getString(8)));
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
	
}
