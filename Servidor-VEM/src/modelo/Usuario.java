package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.collections.ObservableList;

public class Usuario {
	private Integer id;
	private String cedula;
	private String cargo;
	private String nombre;
	private String usuario;
	private String password;
	private String ruta_img;
	private String img;
	private String codigoHuella;
	
	public Usuario(Integer id , String cedula, String cargo,String nombre, String usuario, String password, String ruta_img, String codigoHuella) {
		this.id=id;
		this.cedula=cedula;
		this.nombre=nombre;
		this.cargo=cargo;
		this.usuario=usuario;
		this.password=password;
		this.ruta_img=ruta_img;
		this.codigoHuella=codigoHuella;
	}
	
	public Usuario( String cedula, String cargo,String nombre, String usuario, String password, String ruta_img) {
		this.cedula=cedula;
		this.nombre=nombre;
		this.cargo=cargo;
		this.usuario=usuario;
		this.password=password;
		this.ruta_img=ruta_img;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
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
	public String guardarRegistro(Connection connection)  throws IOException {
		String sql = "select ingresar_usuario(?,?,?,?,?,?);";
		try {
			System.out.println("La ruta es: "+getRuta_img());
			File img = new File(getRuta_img());
			FileInputStream fis = new FileInputStream(img);
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setString(1, getCedula());
			instruccion.setString(2,getCargo() );
			instruccion.setString(3,getNombre() );
			instruccion.setString(4, getUsuario());
			instruccion.setString(5,getPassword());
			instruccion.setBinaryStream(6, fis, (int)img.length());
			instruccion.execute();
			return getCedula();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public InputStream consultarImg(Connection connection) throws FileNotFoundException {
		String sql = "select img_user from User_VE where cedula_user=?";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setString(1, getCedula());
			
			ResultSet resultado = instruccion.executeQuery();
			InputStream is = null;
			while(resultado.next()) {
				is = resultado.getBinaryStream(1);
			}
			return is;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	public String getRuta_img() {
		return ruta_img;
	}

	public void setRuta_img(String ruta_img) {
		this.ruta_img = ruta_img;
	}
}
