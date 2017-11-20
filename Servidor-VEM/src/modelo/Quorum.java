package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Quorum {
	private Date fecha_quorum;

	public Date getFecha_quorum() {
		return fecha_quorum;
	}

	public void setFecha_quorum(Date fecha_quorum) {
		this.fecha_quorum = fecha_quorum;
	}
	
	public Quorum(Date fecha_quorum) {
		this.fecha_quorum = fecha_quorum;
	}
	
	public int guardarRegistro(Connection connection) {
		String sql = "insert into Quorum_VE (fecha_quorum)values(?);";
		try {
			PreparedStatement instruccion = connection.prepareStatement(sql);
			instruccion.setDate(1, fecha_quorum);
			instruccion.execute();
			Statement statement = connection.createStatement();
			ResultSet resultado = statement.executeQuery("select id_quorum from Quorum_VE where fecha_quorum='"+fecha_quorum+"';");
			int id = 0;
			resultado.next();
			id=resultado.getInt(1);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}

}
