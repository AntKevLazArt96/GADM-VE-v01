package servidor;

import java.rmi.RemoteException;

import gad.manta.common.IServidor;
import java.sql.*;

public class Servidor implements IServidor {
	


	@Override
	public String saludar(String nombre) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Tu nombre es: "+ nombre);
		return "Hola "+ nombre +", bienvenido al Sistema";
	}

	@Override
	public String login(String username, String password) throws RemoteException {
		String resultado_validacion="";
		try {
		//para verificar si esta instalado el drive de postgressql
		
		try {
			Class.forName("org.postgresql.Driver");
			
		}catch(ClassNotFoundException cnfe){
			System.out.println("Drive no encontrado");
			cnfe.printStackTrace();
			
		}
		//conección a la base de datos  
		Connection db = DriverManager.getConnection("jdbc:postgresql:gad_voto","postgres","1234");
		Statement st = db.createStatement();
		
		//ejecucion y resultado de la consulta
		ResultSet resultado = st.executeQuery("select verificar_usuario('"+username+"','"+password+"');");
		resultado.next();
		resultado_validacion= resultado.getString(1);
		
		
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return resultado_validacion;
	}
	
}
