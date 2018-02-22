package gad.manta.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;

public class Sesion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7128567888400860325L;
	private String convocatoria;
	private String description;
	private String tipo_sesion;
	private Date fechaRegistro;
	private Date fechaIntervencion;
	private String horaIntervencion;
	private int id_quorum;
	private int id_pdf;
	private String estado_sesion;
	
	public Sesion() {
		// TODO Auto-generated constructor stub
	}
	
	public Sesion(String convocatoria, String description, String tipo_sesion, Date fechaRegistro,
			Date fechaIntervencion, String horaIntervencion, int quorum, int id_pdf) {
		super();
		this.convocatoria = convocatoria;
		this.description = description;
		this.tipo_sesion = tipo_sesion;
		this.fechaRegistro = fechaRegistro;
		this.fechaIntervencion = fechaIntervencion;
		this.horaIntervencion = horaIntervencion;
		this.id_quorum = quorum;
		this.id_pdf = id_pdf;

	}

	public String getTipo_sesion() {
		return tipo_sesion;
	}

	public void setTipo_sesion(String tipo_sesion) {
		this.tipo_sesion = tipo_sesion;
	}

	public String getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(String convocatoria) {
		this.convocatoria = convocatoria;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaIntervencion() {
		return fechaIntervencion;
	}

	public void setFechaIntervencion(Date fechaIntervencion) {
		this.fechaIntervencion = fechaIntervencion;
	}

	public String getHoraIntervencion() {
		return horaIntervencion;
	}

	public void setHoraIntervencion(String horaIntervencion) {
		this.horaIntervencion = horaIntervencion;
	}

	public int getId_quorum() {
		return id_quorum;
	}

	public void setId_quorum(int id_quorum) {
		this.id_quorum = id_quorum;
	}

	public int getId_pdf() {
		return id_pdf;
	}

	public void setId_pdf(int id_pdf) {
		this.id_pdf = id_pdf;
	}

	public String toString() {
		return convocatoria;
	}

	public Sesion(String convocatoria, String description, String estado, Date intervencion, String tipo_sesion) {
		this.convocatoria = convocatoria;
		this.description = description;
		this.estado_sesion = estado;
		this.fechaIntervencion = intervencion;
		this.tipo_sesion = tipo_sesion;
	}

	public Sesion(String convocatoria, String description, Date registro, Date intervencion, String hora) {
		this.convocatoria = convocatoria;
		this.description = description;
		this.fechaRegistro = registro;
		this.fechaIntervencion = intervencion;
		this.horaIntervencion = hora;
	}

	public Sesion(String convocatoria, String description, Date registro, Date intervencion, String hora, int idPdf) {
		this.convocatoria = convocatoria;
		this.description = description;
		this.fechaRegistro = registro;
		this.fechaIntervencion = intervencion;
		this.horaIntervencion = hora;
		this.id_pdf = idPdf;
	}

	public Sesion(String convocatoria, String titulo) {

		this.convocatoria = convocatoria;
		this.description = titulo;
	}

	public Sesion(int id_quorum, String convocatoria) {
		this.id_quorum = id_quorum;
		this.convocatoria = convocatoria;
	}

	public Sesion(String convocatoria, String description, int id_pdf) {
		this.convocatoria = convocatoria;
		this.description = description;
		this.id_pdf = id_pdf;
	}

	public Sesion(String convocatoria, String description, String tipo_sesion, Date fechaRegistro,
			Date fechaIntervencion, String horaIntervencion) {
		this.convocatoria = convocatoria;
		this.description = description;
		this.tipo_sesion = tipo_sesion;
		this.fechaRegistro = fechaRegistro;
		this.fechaIntervencion = fechaIntervencion;
		this.horaIntervencion = horaIntervencion;
	}

	public Sesion(String convocatoria, String tipo_sesion, Date fechaIntervencion) {
		this.convocatoria=convocatoria;
		this.tipo_sesion=tipo_sesion;
		this.fechaIntervencion=fechaIntervencion;
	}

	public String guardarRegistro(Connection connection) {
		String sql = "select * from ingresar_sesion(?,?,?,?,?,?,?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setString(1, getConvocatoria());
			instruccion.setString(2, getDescription());
			instruccion.setString(3, getTipo_sesion());
			instruccion.setDate(4, getFechaRegistro());
			instruccion.setDate(5, getFechaIntervencion());
			instruccion.setString(6, getHoraIntervencion());
			instruccion.setInt(7, getId_pdf());
			instruccion.execute();

			return getConvocatoria();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void llenarInformacion_sesion(Connection connection, ObservableList<Sesion> sesio) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select * from Sesion_VE");
			while (resultado.next()) {
				sesio.add(new Sesion(resultado.getString(1), resultado.getString(2), resultado.getString(3),
						resultado.getDate(4), resultado.getDate(5), resultado.getString(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void llenarInformacion(Connection connection, ObservableList<Usuario> users) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select *from User_VE where position_user='Concejal Principal';");
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

	public static Sesion haySesionParaHoy(Connection connection, Date fechaIntervencion) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select * from Sesion_VE where intervention_sesion='"
					+ fechaIntervencion + "' and estado_sesion='PENDIENTE';");
			if (resultado.next()) {
				Sesion sesion = new Sesion(resultado.getString(1), resultado.getString(3),resultado.getDate(5));
				return sesion;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public static Sesion consultarConvocatoria(Connection connection, String convocatoria) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select * from Sesion_VE where convocatoria_sesion='"+convocatoria+"';");
			if (resultado.next()) {
				Sesion sesion = new Sesion(resultado.getString(1), resultado.getString(3),resultado.getDate(5));
				return sesion;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public int addQuorum(Connection connection) {
		String sql = "update Sesion_VE set id_quorum =? where convocatoria_sesion=?;";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			System.out.println(getId_quorum());
			instruccion.setInt(1, getId_quorum());
			System.out.println("la convocatoria es" + getConvocatoria());
			instruccion.setString(2, getConvocatoria());

			instruccion.execute();

			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	public String getEstado_sesion() {
		return estado_sesion;
	}

	public void setEstado_sesion(String estado_sesion) {
		this.estado_sesion = estado_sesion;
	}

}
