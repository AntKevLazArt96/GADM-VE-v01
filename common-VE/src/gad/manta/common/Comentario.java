package gad.manta.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Comentario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3290481556717104749L;
	private int id_user;
	private int id_pdf;
	private String nota;
	

	public Comentario(int id_user, int id_pdf, String nota) {
		this.id_user = id_user;
		this.id_pdf = id_pdf;
		this.nota = nota;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public int getId_pdf() {
		return id_pdf;
	}

	public void setId_pdf(int id_pdf) {
		this.id_pdf = id_pdf;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	/*public int guardarNota_pdf(Connection connection) {
		String sql = "insert into notaspdf_ve (id_user, id_pdf, descripcion_notas)values(?,?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, id_user);
			instruccion.setInt(2, id_pdf);
			instruccion.setString(3, nota);
			instruccion.execute();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int guardarNota_Acta(Connection connection) {
		String sql = "insert into notasActa_ve (id_user, id_acta, descripcion_notas)values(?,?,?);";
		try {
			System.out.println(nota);
			System.out.println(id_pdf);
			System.out.println(id_user);
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, id_user);
			instruccion.setInt(2,id_pdf);
			instruccion.setString(3, nota);
			instruccion.execute();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}*/
}
