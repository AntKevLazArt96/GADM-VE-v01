package gad.manta.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OrdenDia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -197902119814967784L;
	private int id;
	private int numeroPunto;
	private String tema;
	private int proponente;
	private String convocatoria;
	private String proponente_nombre;

	public int getProponente() {
		return proponente;
	}

	public String getProponente_nombre() {
		return proponente_nombre;
	}

	public void setProponente_nombre(String proponente_nombre) {
		this.proponente_nombre = proponente_nombre;
	}

	public void setProponente(int proponente) {
		this.proponente = proponente;
	}

	public int getNumeroPunto() {
		return numeroPunto;
	}

	public void setNumeroPunto(int numeroPunto) {
		this.numeroPunto = numeroPunto;
	}

	public String getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrdenDia(int id, int numeroPunto, String tema) {
		super();
		this.id = id;
		this.numeroPunto = numeroPunto;
		this.tema = tema;
	}

	public OrdenDia(String convocatoria, int numeroPunto, String tema, int proponente) {
		this.convocatoria = convocatoria;
		this.numeroPunto = numeroPunto;
		this.tema = tema;
		this.proponente = proponente;
	}

	public OrdenDia(int id, String convocatoria, int numeroPunto, String tema, int proponente) {
		this.id = id;
		this.convocatoria = convocatoria;
		this.numeroPunto = numeroPunto;
		this.tema = tema;
		this.proponente = proponente;

	}

	public OrdenDia(int numeroPunto, String tema, int proponente) {
		super();
		this.numeroPunto = numeroPunto;
		this.tema = tema;
		this.proponente = proponente;
	}

	public OrdenDia(int numeroPunto, String tema, String proponente_nombre) {
		this.numeroPunto = numeroPunto;
		this.tema = tema;
		this.proponente_nombre = proponente_nombre;
	}

	public OrdenDia(int id, int numeroPunto, String tema, int proponente) {
		super();
		this.id = id;
		this.numeroPunto = numeroPunto;
		this.tema = tema;
		this.proponente = proponente;
	}

	public OrdenDia(int id, int numeroPunto, String tema, String proponente) {
		super();
		this.id = id;
		this.numeroPunto = numeroPunto;
		this.tema = tema;
		this.proponente_nombre = proponente;
	}

	public int guardarRegistro(Connection connection) {
		if (proponente == 0) {
			String sql = "insert into ordendia_ve (convocatoria_sesion,numpunto_ordendia,descrip_ordendia)values(?,?,?)";
			try {
				PreparedStatement instruccion = connection.prepareStatement(sql);
				instruccion.setString(1, convocatoria);
				instruccion.setInt(2, numeroPunto);
				instruccion.setString(3, tema);
				instruccion.execute();
				Statement statement = connection.createStatement();
				ResultSet resultado = statement
						.executeQuery("select id_ordendia from OrdenDia_VE order by id_ordendia desc limit  1");
				int id = 0;
				resultado.next();
				id = resultado.getInt(1);
				System.out.println("El id orden da es "+id);
				setId(id);
				return id;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		} else {
			String sql = "select *from ingresar_orden_dia(?,?,?,?);";
			try {
				PreparedStatement instruccion = connection.prepareStatement(sql);
				instruccion.setString(1, convocatoria);
				instruccion.setInt(2, numeroPunto);
				instruccion.setString(3, tema);

				instruccion.setInt(4, proponente);
				instruccion.execute();
				Statement statement = connection.createStatement();
				ResultSet resultado = statement
						.executeQuery("select id_ordendia from OrdenDia_VE order by id_ordendia desc limit  1");
				int id = 0;
				resultado.next();
				id = resultado.getInt(1);
				return id;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}

	}

	public int actualizarRegistro(Connection connection,int id) {
		if(proponente==0) {
			String sql = "UPDATE ordendia_ve SET numpunto_ordendia=?, descrip_ordendia=? WHERE id_ordendia=?;";
			try {
				PreparedStatement instruccion = connection.prepareStatement(sql);
				instruccion.setInt(1, numeroPunto);
				instruccion.setString(2, tema);
				instruccion.setInt(3, id);
				instruccion.execute();
				return getId();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}else {
			String sql = "UPDATE ordendia_ve SET numpunto_ordendia=?, descrip_ordendia=?, id_user=? WHERE id_ordendia=?;";
			try {
				PreparedStatement instruccion = connection.prepareStatement(sql);
				instruccion.setInt(1, numeroPunto);
				instruccion.setString(2, tema);
				instruccion.setInt(3, proponente);
				instruccion.setInt(4, id);
				instruccion.execute();
				return getId();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		}
		
	}

	public static void llenarInformacion(Connection connection, List<OrdenDia> orden, String convocatoria) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement
					.executeQuery("select *from OrdenDia_VE where convocatoria_sesion='" + convocatoria + "';");
			while (resultado.next()) {
				orden.add(new OrdenDia(resultado.getInt(1), resultado.getString(2), resultado.getInt(3),
						resultado.getString(4), resultado.getInt(5)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
