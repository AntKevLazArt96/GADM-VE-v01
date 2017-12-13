package servidor;

import java.rmi.RemoteException;

import gad.manta.common.ActaPdf;
import gad.manta.common.Comentario;
import gad.manta.common.Conexion;
import gad.manta.common.Config;
import gad.manta.common.Documentacion;
import gad.manta.common.IServidor;
import gad.manta.common.OrdenDia;
import gad.manta.common.Pdf;
import gad.manta.common.Usuario;
import gad.manta.common.Voto;
import gad.manta.common.data_configuracion;
import gad.manta.common.Sesion;
//import modelo.Sesion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Servidor implements IServidor {
	public Servidor() throws RemoteException {
		super();
	}
	Conexion conexion = new Conexion();
	List<Usuario> listaUsuario = new ArrayList<>();
	// votacion para aprobar el orden del dia propuesto
	List<Voto> listaVotantes = new ArrayList<>();
	List<Voto> listaVotoAprueba = new ArrayList<>();
	List<Voto> listaVotoRechaza = new ArrayList<>();
	// votacion para aprobar cada punto de ser necesario
	List<Voto> listaVotantesPunto = new ArrayList<>();
	List<Voto> listaVotoAFavor = new ArrayList<>();
	List<Voto> listaVotoEnContra = new ArrayList<>();
	List<Voto> listaVotoSalvado = new ArrayList<>();
	List<Voto> listaVotoBlanco = new ArrayList<>();

	List<Usuario> listaPedirPalabra = new ArrayList<>();

	Calendar fecha = new GregorianCalendar();
	int annio = fecha.get(Calendar.YEAR);
	int mes = fecha.get(Calendar.MONTH) + 1;
	int dia = fecha.get(Calendar.DAY_OF_MONTH);

	@Override
	public String login(String username, String password) throws RemoteException {

		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();
			
			int id;
			String usuario = "";
			byte[] img = null;
			// ejecucion y resultado de la consulta
			ResultSet resultado = st
					.executeQuery("select *from verificar_usuario('" + username + "','" + password + "');");
			resultado.next();
			id = resultado.getInt(1);
			usuario = resultado.getString(2);
			img = resultado.getBytes(3);
			db.close();

			// Para el Quorum
			if (!username.equals("secretaria")) {
				listaUsuario.add(new Usuario(id, usuario, "PRESENTE", img));
			}
			System.out.println("el usuario se ha logueado correctamente");

			return usuario;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Usuario usuario(String name) {
		
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from consulta_usuario_name('" + name + "');");
			resultado.next();
			Usuario user = new Usuario(resultado.getInt(1), resultado.getString(2), resultado.getBytes(3));
			db.close();
			return user;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void add_nota_pdf(int id_punto, int id_user, String nota) {
	
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta
			st.executeUpdate("insert into notaspdf_ve (id_user, id_pdf, descripcion_notas)values(" + id_punto + ","
					+ id_user + ",'" + nota + "');");
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	@Override
	public void add_nota_acta(int id_punto, int id_user, String nota) {
		
		try {
			System.out.println(id_punto);
			System.out.println(id_user);
			System.out.println(nota);
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();// ejecucion y resultado de la consulta
			st.executeUpdate("insert into notasActa_ve (id_user, id_acta, descripcion_notas)values(" + id_punto + ","
					+ id_user + ",'" + nota + "');");
		
			conexion.cerrarConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	@Override
	public ActaPdf acta_sesion(int id) {
		
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			ResultSet resultado = st.executeQuery("select * from acta_ve where id_pdf=" + id + ";");
			resultado.next();
			ActaPdf user = new ActaPdf(resultado.getInt(1), resultado.getString(2), resultado.getBytes(3));
			conexion.cerrarConexion();
			return user;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Pdf pdf_punto(int id) {
		
		Pdf user = null;
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select * from pdf_ve where id_pdf=" + id + ";");
			if (resultado.next()) {
				user = new Pdf(resultado.getInt(1), resultado.getInt(2), resultado.getString(3), resultado.getBytes(4));

			}

			conexion.cerrarConexion();
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<Usuario> listaUsuarios() throws RemoteException {
		List<Usuario> listaUsuario = new ArrayList<>();
		
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from consulta_usuarios();");
			while (resultado.next()) {
				listaUsuario.add(new Usuario(resultado.getInt(1), resultado.getString(2), resultado.getBytes(3)));
			}
			conexion.cerrarConexion();
			return listaUsuario;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<Usuario> asistenciaUsuarios(int id_quorum) throws RemoteException {
		List<Usuario> listaUsuario = new ArrayList<>();
		
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from asistencia_concejales(" + id_quorum + ");");
			while (resultado.next()) {
				listaUsuario.add(new Usuario(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getBytes(4)));
			}
			conexion.cerrarConexion();
			return listaUsuario;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public int presentes() throws RemoteException {
		
		try {
		
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select count(*) from Asistencia_VE where estado_asistencia='PRESENTE'");
			resultado.next();
			int numero = resultado.getInt(1);
			conexion.cerrarConexion();
			return numero;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Usuario> consultaQuorum() throws RemoteException {
		return listaUsuario;
	}
	
	@Override
	public void limpiarQuorum() throws RemoteException {
		listaUsuario.clear();
	}

	@Override
	public String addVoto(String usuario, String voto, byte[] img) throws RemoteException {
		// TODO Auto-generated method stub
		if (voto.contains("APROBADO")) {
			listaVotoAprueba.add(new Voto(usuario, voto, img));
		}
		if (voto.contains("RECHAZADO")) {
			listaVotoRechaza.add(new Voto(usuario, voto, img));
		}
		listaVotantes.add(new Voto(usuario, voto, img));

		return usuario;
	}

	@Override
	public List<Voto> votosAprobados() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoAprueba;
	}

	@Override
	public List<Voto> votosRechazados() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoRechaza;
	}

	@Override
	public List<Voto> votantes() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotantes;
	}

	@Override
	public int agregarSesion(String fechaRegistro, String fechaIntervencion, String horaIntervencion,
			String convocatoria, String titulo) throws RemoteException {
		int idsesion = 0;
		
		try {

			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from ingresar_sesion('" + fechaRegistro + "','"
					+ fechaIntervencion + "','" + horaIntervencion + "','" + convocatoria + "','" + titulo + "');");
			resultado.next();
			idsesion = resultado.getInt(1);
			conexion.cerrarConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idsesion;

	}

	@Override
	public List<String> consultaUsuario() throws RemoteException {
		List<String> listausuario = new ArrayList<>();
		
		try {
		
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select name_user from User_VE;");

			while (resultado.next()) {
				listausuario.add(resultado.getString(1));
			}
			conexion.cerrarConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listausuario;
		// TODO Auto-generated method stub

	}

	@Override
	public Sesion consultarSesion() throws RemoteException {

		try {
			// para verificar si esta instalado el drive de postgressql
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery(
					"select convocatoria_sesion,description_sesion ,id_pdf from Sesion_VE where intervention_sesion='"
							+ annio + "-" + mes + "-" + dia + "';");
			resultado.next();
			Sesion sesion = new Sesion(resultado.getString(1), resultado.getString(2), resultado.getInt(3));
			conexion.cerrarConexion();
			return sesion;

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<Sesion> consultarSesion_Modificacion(String convocatoria) throws RemoteException {
		List<Sesion> listaSesion = new ArrayList<>();
		try {
			// para verificar si esta instalado el drive de postgressql
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st
					.executeQuery("select * from Sesion_VE where convocatoria_sesion='" + convocatoria + "';");

			while (resultado.next()) {
				listaSesion.add(new Sesion(resultado.getString(1), resultado.getString(2), resultado.getString(3),
						resultado.getDate(4), resultado.getDate(5), resultado.getString(6), resultado.getInt(7),
						resultado.getInt(8)));

			}
			conexion.cerrarConexion();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaSesion;
	}

	@Override
	public List<OrdenDia> consultarOrden() throws RemoteException {
		List<OrdenDia> lista_ordendia = new ArrayList<OrdenDia>();
		try {
			// para verificar si esta instalado el drive de postgressql
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery(
					"select id_ordenDia,numpunto_ordendia,descrip_ordendia,us.name_user from Sesion_VE as s inner join OrdenDia_VE as od on s.convocatoria_sesion=od.convocatoria_sesion inner join User_VE as us on us.id_user=od.id_user where s.intervention_sesion='"
							+ annio + "-" + mes + "-" + dia + "';");

			while (resultado.next()) {
				lista_ordendia.add(new OrdenDia(resultado.getInt(1), resultado.getInt(2), resultado.getString(3),
						resultado.getString(4)));
			}
			conexion.cerrarConexion();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return lista_ordendia;
	}

	@Override
	public List<OrdenDia> consultarOrden_Modificacion(String convocatoria) throws RemoteException {
		List<OrdenDia> lista_ordendia = new ArrayList<OrdenDia>();
		try {
			// para verificar si esta instalado el drive de postgressql
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select id_ordendia, numpunto_ordendia,descrip_ordendia \r\n"
					+ "	from Sesion_VE as s inner join OrdenDia_VE as od on s.convocatoria_sesion=od.convocatoria_sesion\r\n"
					+ "	where s.convocatoria_sesion='" + convocatoria + "';");

			while (resultado.next()) {
				lista_ordendia.add(new OrdenDia(resultado.getInt(1), resultado.getInt(2), resultado.getString(3)));
			}
			conexion.cerrarConexion();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return lista_ordendia;
	}

	@Override
	public List<OrdenDia> consultarPunto_Modificacion(int id_punto) throws RemoteException {
		List<OrdenDia> lista_ordendia = new ArrayList<OrdenDia>();
		try {
			// para verificar si esta instalado el drive de postgressql
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select * from OrdenDia_VE where id_ordendia=" + id_punto + ";");

			while (resultado.next()) {
				lista_ordendia.add(new OrdenDia(resultado.getInt(1), resultado.getString(2), resultado.getInt(3),
						resultado.getString(4), resultado.getInt(5)));
			}
			conexion.cerrarConexion();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return lista_ordendia;
	}

	@Override
	public List<Pdf> consultarPDFS_Modificacion(int id_punto) throws RemoteException {
		List<Pdf> lista_PDF = new ArrayList<Pdf>();
		try {
			// para verificar si esta instalado el drive de postgressql
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st
					.executeQuery("select id_pdf,nombre_pdf from pdf_ve where id_ordendia=" + id_punto + ";");

			while (resultado.next()) {
				lista_PDF.add(new Pdf(resultado.getInt(1), resultado.getString(2)));
			}
			conexion.cerrarConexion();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

		return lista_PDF;
	}

	@Override
	public List<Documentacion> mostrarDocumentacion() throws RemoteException {
		List<Documentacion> lista_documentacion = new ArrayList<Documentacion>();

		try {
			// para verificar si esta instalado el drive de postgressql

			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery(
					"select p.id_pdf,numpunto_ordendia, nombre_pdf from Sesion_VE as s	inner join OrdenDia_VE as od on s.convocatoria_sesion=od.convocatoria_sesion inner join Pdf_VE as p on p.id_ordendia=od.id_ordendia where s.intervention_sesion='"
							+ annio + "-" + mes + "-" + dia + "';");
			while (resultado.next()) {

				lista_documentacion
						.add(new Documentacion(resultado.getInt(1), resultado.getInt(2), resultado.getString(3)));
				System.out.println(resultado.getInt(1));
			}
			conexion.cerrarConexion();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return lista_documentacion;
	}

	// Votaciones del orden del dia
	@Override
	public String addVotoPunto(String usuario, String voto, byte[] img) throws RemoteException {
		// TODO Auto-generated method stub
		if (voto.contains("PROPONENTE A FAVOR")) {
			listaVotoAFavor.add(new Voto(usuario, voto, img));
		} else {
			if (voto.contains("A FAVOR")) {
				listaVotoAFavor.add(new Voto(usuario, voto, img));
			}
		}

		if (voto.contains("EN CONTRA")) {
			listaVotoEnContra.add(new Voto(usuario, voto, img));
		}
		if (voto.contains("VOTO SALVADO")) {
			listaVotoSalvado.add(new Voto(usuario, voto, img));
		}
		if (voto.contains("EN BLANCO")) {
			listaVotoBlanco.add(new Voto(usuario, voto, img));
		}
		listaVotantesPunto.add(new Voto(usuario, voto, img));

		return usuario;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public String reiniciarVoto(String user, String voto, int index, int index2) throws RemoteException {

		if (voto.contains("PROPONENTE A FAVOR")) {
			listaVotoAFavor.remove(index2);
		} else {
			if (voto.contains("A FAVOR")) {
				listaVotoAFavor.remove(index2);
			}
		}

		if (voto.contains("EN CONTRA")) {
			listaVotoEnContra.remove(index2);
		}
		if (voto.contains("VOTO SALVADO")) {
			listaVotoSalvado.remove(index2);
		}
		if (voto.contains("EN BLANCO")) {
			listaVotoBlanco.remove(index2);
		}

		listaVotantesPunto.remove(index);

		return "El usuario " + user + " ha sido borrado correctamente del registro";
	}

	@Override
	public List<Voto> votantesPunto() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotantesPunto;
	}

	@Override
	public List<Voto> votosAFavor() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoAFavor;
	}

	@Override
	public List<Voto> votosEnContra() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoEnContra;
	}

	@Override
	public List<Voto> votosSalvados() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoSalvado;
	}

	@Override
	public List<Voto> votosBlanco() throws RemoteException {
		// TODO Auto-generated method stub
		return listaVotoBlanco;
	}

	@Override
	public String limpiarVoto() throws RemoteException {
		// TODO Auto-generated method stub

		try {
			listaVotantesPunto.clear();
			listaVotoAFavor.clear();
			listaVotoEnContra.clear();
			listaVotoSalvado.clear();
			listaVotoBlanco.clear();
			return "Se ha reiniciado los votos";
		} catch (Exception e) {
			// TODO: handle exception
			return "Error: " + e.getMessage() + "y su causa es" + e.getCause();
		}

	}

	@Override
	public String limpiarVotoOrden() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			listaVotantes.clear();
			listaVotoAprueba.clear();
			listaVotoRechaza.clear();
			return "Se ha reiniciado los votos";
		} catch (Exception e) {
			// TODO: handle exception
			return "Error: " + e.getMessage() + "y su causa es" + e.getCause();
		}
	}

	@Override
	public List<Pdf> consultarPdfsPunto(int id_ordendia) throws RemoteException {
		List<Pdf> listaPdfsOrdenDia = new ArrayList<>();
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado d la consulta
			ResultSet resultado = st.executeQuery("select * from pdf_ve where id_ordendia=" + id_ordendia + ";");
			while (resultado.next()) {
				Pdf pdf = new Pdf(resultado.getInt(1), resultado.getInt(2), resultado.getString(3),
						resultado.getBytes(4));
				listaPdfsOrdenDia.add(pdf);
			}
			conexion.cerrarConexion();
			return listaPdfsOrdenDia;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String guardarVotos(int id_ordendia, int si, int no, int blanco, int salvo) throws RemoteException {
		String estado = null;
		if (si > no) {
			estado = "APROBADO";
		}
		if (no > si) {
			estado = "RECHAZADO";
		}
		if (si == 0 && no == 0 && blanco == 0 && salvo == 0) {
			estado = "NO SE VOTO";
		}

		try {
			// para verificar si esta instalado el drive de postgressql

			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();

			PreparedStatement instruccion = db.prepareStatement(
					"update OrdenDia_VE set si_ordendia=?, no_ordendia=?, blanco_ordendia=?, salvo_ordendia=?, estado_ordendia=?, verifica_ordendia='TERMINADO' where id_ordendia=?;");
			instruccion.setInt(1, si);
			instruccion.setInt(2, no);
			instruccion.setInt(3, blanco);
			instruccion.setInt(4, salvo);
			instruccion.setString(5, estado);
			instruccion.setInt(6, id_ordendia);

			instruccion.execute();
			conexion.cerrarConexion();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return estado;
	}

	@Override
	public String verificarSiSeVoto(int id_ordendia) throws RemoteException {
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st
					.executeQuery("select verifica_ordendia from OrdenDia_VE where id_ordendia=" + id_ordendia + ";");
			conexion.cerrarConexion();
			resultado.next();
			return resultado.getString(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String verificarSiTerminoSesion(String convocatoria) throws RemoteException {
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			// ejecucion y resultado de la consulta
			ResultSet resultado = st
					.executeQuery("select verifica_ordendia from OrdenDia_VE where convocatoria_sesion='" + convocatoria
							+ "' group by verifica_ordendia;");
			conexion.cerrarConexion();
			int contador = 0;
			String estado = "NO HAY ESTADO";
			while (resultado.next()) {
				estado = resultado.getString(1);
				contador++;
			}

			if (contador == 1) {
				return estado;
			} else {
				return "NO HA TERMINADO";
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@Override
	public String TerminarSesion(String convocatoria) throws RemoteException {
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			PreparedStatement instruccion = db
					.prepareStatement("update Sesion_VE set estado_sesion='TERMINADO' where convocatoria_sesion=?;");
			instruccion.setString(1, convocatoria);
			instruccion.execute();
			conexion.cerrarConexion();
			return "TERMINADO";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Config obtenerConfiguracion() throws RemoteException {
		try {
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			ResultSet resultado = st.executeQuery("select * from configuracion_ve where id_confi=1;");
			resultado.next();

			Config config = new Config(resultado.getString(3), resultado.getInt(5));
			// socket
			conexion.cerrarConexion();
			return config;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String pedirPalabra(int id, String usuario) throws RemoteException {
		listaPedirPalabra.add(new Usuario(id, usuario));
		return "El usuario " + usuario + " ha pedido la palabra";
	}

	@Override
	public List<Usuario> listaPalabraPedida() throws RemoteException {
		// TODO Auto-generated method stub
		return listaPedirPalabra;
	}

	@Override
	public Usuario obtener_img(String username) throws RemoteException {

		try {
			// conecci�n a la base de datos
			conexion.establecerConexion();
			// conecci�n a la base de datos
			Connection db =conexion.getConnection();
			Statement st = db.createStatement();	// ejecucion y resultado de la consulta

			int id;
			String usuario = "";
			byte[] img = null;
			// ejecucion y resultado de la consulta
			ResultSet resultado = st.executeQuery("select *from obtener_img('" + username + "');");
			resultado.next();
			id = resultado.getInt(1);
			usuario = resultado.getString(2);
			img = resultado.getBytes(3);
			conexion.cerrarConexion();
			Usuario user = new Usuario(id, usuario, img);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

}
