package gad.manta.common;

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

public class Imagen {
	private int id_imd;
	private String nombre_img;
	private String ruta_img;
	
	public int getId_imd() {
		return id_imd;
	}
	public void setId_imd(int id_imd) {
		this.id_imd = id_imd;
	}
	public String getRuta_img() {
		return ruta_img;
	}
	public void setRuta_img(String ruta_img) {
		this.ruta_img = ruta_img;
	}
	
	public String getNombre_img() {
		return nombre_img;
	}
	public void setNombre_img(String nombre_img) {
		this.nombre_img = nombre_img;
	}
	
	public Imagen() {
		
	}
	
	public Imagen(int id_img) {
		this.id_imd=id_img;
	}
	
	public Imagen(String nombre, String ruta) {
		this.nombre_img=nombre;
		this.ruta_img=ruta;
	}
	
	public int guardarRegistro(Connection connection)  throws IOException {
		String sql = "insert into Img_VE (nombre_img,img)values(?,?);";
		try {
			File img = new File(getRuta_img());
			FileInputStream fis = new FileInputStream(img);
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setString(1, getNombre_img());
			instruccion.setBinaryStream(2, fis, (int)img.length());
			instruccion.execute();
			
			
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select *from Img_VE where nombre_img='"+getNombre_img()+"';");
			int id_img =0;
			while(resultado.next()) {
				id_img= resultado.getInt(1);
			}
			
			return id_img;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public InputStream consultarImg(int id_img,Connection connection) throws FileNotFoundException {
		String sql = "select img from Img_VE where id_img=?";
		try {
			System.out.println(id_img);
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, id_img);
			
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
	
	public int eliminarImg(int id_img,Connection connection) {
		String sql = "delete from Img_VE where id_img=?";
		
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, id_img);
			instruccion.execute();
			
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
}
