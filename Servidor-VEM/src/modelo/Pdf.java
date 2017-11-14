<<<<<<< HEAD
package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pdf {
	private int id;
	private int id_orden_dia;
	private String ruta_pdf;
	
	public Pdf(int id, int id_orden_dia, String ruta_pdf) {
		this.id = id;
		this.id_orden_dia = id_orden_dia;
		this.ruta_pdf = ruta_pdf;
	}
	public Pdf(int id_orden_dia, String ruta_pdf) {
		this.id_orden_dia = id_orden_dia;
		this.ruta_pdf = ruta_pdf;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_orden_dia() {
		return id_orden_dia;
	}

	public void setId_orden_dia(int id_orden_dia) {
		this.id_orden_dia = id_orden_dia;
	}

	public String getRuta_pdf() {
		return ruta_pdf;
	}

	public void setRuta_pdf(String ruta_pdf) {
		this.ruta_pdf = ruta_pdf;
	}

	public int guardarRegistro_pdf(Connection connection) {
		String sql = "INSERT INTO pdf(id_orden_dia,ruta_pdf) VALUES (?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, id_orden_dia);
			instruccion.setString(2, ruta_pdf );
			instruccion.execute();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	


}
=======
package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pdf {
	private int id;
	private int id_orden_dia;
	private String ruta_pdf;
	
	public Pdf(int id, int id_orden_dia, String ruta_pdf) {
		this.id = id;
		this.id_orden_dia = id_orden_dia;
		this.ruta_pdf = ruta_pdf;
	}
	public Pdf(int id_orden_dia, String ruta_pdf) {
		this.id_orden_dia = id_orden_dia;
		this.ruta_pdf = ruta_pdf;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_orden_dia() {
		return id_orden_dia;
	}

	public void setId_orden_dia(int id_orden_dia) {
		this.id_orden_dia = id_orden_dia;
	}

	public String getRuta_pdf() {
		return ruta_pdf;
	}

	public void setRuta_pdf(String ruta_pdf) {
		this.ruta_pdf = ruta_pdf;
	}

	public int guardarRegistro_pdf(Connection connection) {
		String sql = "INSERT INTO pdf(id_orden_dia,ruta_pdf) VALUES (?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setInt(1, id_orden_dia);
			instruccion.setString(2, ruta_pdf );
			instruccion.execute();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	


}
>>>>>>> anthony
