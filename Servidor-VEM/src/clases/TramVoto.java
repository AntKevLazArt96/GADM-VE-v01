package clases;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import org.json.simple.JSONObject;
import application.InicioVotoCtrl;
import application.LoginController;
import application.PantallaPrincipalCtrl;
import gad.manta.common.Usuario;
import gad.manta.common.Voto;
import javafx.scene.image.Image;

public class TramVoto {

	// Listas
	List<Voto> lista;
	List<Voto> favor;
	List<Voto> contra ;
	List<Voto> salvados;
	List<Voto> blanco;

	public static InicioVotoCtrl iv;

	void limpiar() {
		iv.img1.setVisible(false);
		iv.img2.setVisible(false);
		iv.img3.setVisible(false);
		iv.img4.setVisible(false);
		iv.img5.setVisible(false);
		iv.img6.setVisible(false);
		iv.img7.setVisible(false);
		iv.img8.setVisible(false);
		iv.img9.setVisible(false);
		iv.img10.setVisible(false);
		iv.img11.setVisible(false);
		iv.img12.setVisible(false);
		iv.user1.setVisible(false);
		iv.user2.setVisible(false);
		iv.user3.setVisible(false);
		iv.user4.setVisible(false);
		iv.user5.setVisible(false);
		iv.user6.setVisible(false);
		iv.user7.setVisible(false);
		iv.user8.setVisible(false);
		iv.user9.setVisible(false);
		iv.user10.setVisible(false);
		iv.user11.setVisible(false);
		iv.user12.setVisible(false);
		iv.status1.setVisible(false);
		iv.status2.setVisible(false);
		iv.status3.setVisible(false);
		iv.status4.setVisible(false);
		iv.status5.setVisible(false);
		iv.status6.setVisible(false);
		iv.status7.setVisible(false);
		iv.status8.setVisible(false);
		iv.status9.setVisible(false);
		iv.status10.setVisible(false);
		iv.status11.setVisible(false);
		iv.estatus12.setVisible(false);
		iv.btnRe1.setVisible(false);
		iv.btnRe2.setVisible(false);
		iv.btnRe3.setVisible(false);
		iv.btnRe4.setVisible(false);
		iv.btnRe5.setVisible(false);
		iv.btnRe6.setVisible(false);
		iv.btnRe7.setVisible(false);
		iv.btnRe8.setVisible(false);
		iv.btnRe9.setVisible(false);
		iv.btnRe10.setVisible(false);
		iv.btnRe11.setVisible(false);
		iv.btnRe12.setVisible(false);
		iv.status1.setStyle("-fx-text-fill: #4caf50;");
		iv.status2.setStyle("-fx-text-fill: #4caf50;");
		iv.status3.setStyle("-fx-text-fill: #4caf50;");
		iv.status4.setStyle("-fx-text-fill: #4caf50;");
		iv.status5.setStyle("-fx-text-fill: #4caf50;");
		iv.status6.setStyle("-fx-text-fill: #4caf50;");
		iv.status7.setStyle("-fx-text-fill: #4caf50;");
		iv.status8.setStyle("-fx-text-fill: #4caf50;");
		iv.status9.setStyle("-fx-text-fill: #4caf50;");
		iv.status10.setStyle("-fx-text-fill: #4caf50;");
		iv.status11.setStyle("-fx-text-fill: #4caf50;");
		iv.estatus12.setStyle("-fx-text-fill: #4caf50;");
	}
	
	public void tramiteVoto() {
		try {
			// lista que devuelve todos los votantes con sus votos
			lista = LoginController.servidor.votantesPunto();
			favor = LoginController.servidor.votosAFavor();
			contra = LoginController.servidor.votosEnContra();
			salvados = LoginController.servidor.votosSalvados();
			blanco = LoginController.servidor.votosBlanco();
			 
			
			
			// lista que verifica el total de asistentes
			List<Usuario> quorum = LoginController.servidor.consultaQuorum();

			VotoResumen.si =favor.size();
			VotoResumen.no=contra.size();
			VotoResumen.blanco=blanco.size();
			VotoResumen.salvo=salvados.size();
			
			iv.lblAFavor.setText(String.valueOf(favor.size()));
			iv.lblEnContra.setText(String.valueOf(contra.size()));
			iv.lblSalvoVoto.setText(String.valueOf(salvados.size()));
			iv.lblBlanco.setText(String.valueOf(blanco.size()));
			
			iv.lblEspera.setText(String.valueOf(quorum.size() - lista.size()));

			// reiniciando los usuarios
			if (lista.size() == 0) {
				limpiar();
			}

			for (int i = 0; i < lista.size(); i++) {
				if (i == 0) {
					iv.img1.setVisible(true);
					iv.img1.setImage(convertirImg(lista.get(i).getImg()));
					iv.user1.setVisible(true);
					iv.user1.setText(lista.get(i).getNombre());
					iv.status1.setText(lista.get(i).getVoto());
					iv.status1.setVisible(true);
					iv.btnRe1.setVisible(true);
					//cambiamos el color de acuerdo al voto
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status1.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status1.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status1.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 1) {
					iv.img2.setImage(convertirImg(lista.get(i).getImg()));
					iv.img2.setVisible(true);
					iv.user2.setText(lista.get(i).getNombre());
					iv.user2.setVisible(true);
					iv.status2.setText(lista.get(i).getVoto());
					iv.status2.setVisible(true);
					iv.btnRe2.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status2.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status2.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status2.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 2) {
					iv.img3.setImage(convertirImg(lista.get(i).getImg()));
					iv.img3.setVisible(true);
					iv.user3.setText(lista.get(i).getNombre());
					iv.user3.setVisible(true);
					iv.status3.setText(lista.get(i).getVoto());
					iv.status3.setVisible(true);
					iv.btnRe3.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status3.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status3.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status3.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 3) {
					iv.img4.setImage(convertirImg(lista.get(i).getImg()));
					iv.img4.setVisible(true);
					iv.user4.setText(lista.get(i).getNombre());
					iv.user4.setVisible(true);
					iv.status4.setText(lista.get(i).getVoto());
					iv.status4.setVisible(true);
					iv.btnRe4.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status4.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status4.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status4.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 4) {
					iv.img5.setImage(convertirImg(lista.get(i).getImg()));
					iv.img5.setVisible(true);
					iv.user5.setText(lista.get(i).getNombre());
					iv.user5.setVisible(true);
					iv.status5.setText(lista.get(i).getVoto());
					iv.status5.setVisible(true);
					iv.btnRe5.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status5.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status5.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status5.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 5) {
					iv.img6.setImage(convertirImg(lista.get(i).getImg()));
					iv.img6.setVisible(true);
					iv.user6.setText(lista.get(i).getNombre());
					iv.user6.setVisible(true);
					iv.status6.setText(lista.get(i).getVoto());
					iv.status6.setVisible(true);
					iv.btnRe6.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status6.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status6.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status6.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 6) {
					iv.img7.setImage(convertirImg(lista.get(i).getImg()));
					iv.img7.setVisible(true);
					iv.user7.setText(lista.get(i).getNombre());
					iv.user7.setVisible(true);
					iv.status7.setText(lista.get(i).getVoto());
					iv.status7.setVisible(true);
					iv.btnRe7.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status7.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status7.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status7.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 7) {
					iv.img8.setImage(convertirImg(lista.get(i).getImg()));
					iv.img8.setVisible(true);
					iv.user8.setText(lista.get(i).getNombre());
					iv.user8.setVisible(true);
					iv.status8.setText(lista.get(i).getVoto());
					iv.status8.setVisible(true);
					iv.btnRe8.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status8.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status8.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status8.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 8) {
					iv.img9.setImage(convertirImg(lista.get(i).getImg()));
					iv.img9.setVisible(true);
					iv.user9.setText(lista.get(i).getNombre());
					iv.user9.setVisible(true);
					iv.status9.setText(lista.get(i).getVoto());
					iv.status9.setVisible(true);
					iv.btnRe9.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status9.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status9.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status9.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 9) {
					iv.img10.setImage(convertirImg(lista.get(i).getImg()));
					iv.img10.setVisible(true);
					iv.user10.setText(lista.get(i).getNombre());
					iv.user10.setVisible(true);
					iv.status10.setText(lista.get(i).getVoto());
					iv.status10.setVisible(true);
					iv.btnRe10.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status10.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status10.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status10.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 10) {
					iv.img11.setImage(convertirImg(lista.get(i).getImg()));
					iv.img11.setVisible(true);
					iv.user11.setText(lista.get(i).getNombre());
					iv.user11.setVisible(true);
					iv.status11.setText(lista.get(i).getVoto());
					iv.status11.setVisible(true);
					iv.btnRe11.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.status11.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.status11.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.status11.setStyle("-fx-text-fill: black;");
					}
				}
				if (i == 11) {
					iv.img12.setImage(convertirImg(lista.get(i).getImg()));
					iv.img12.setVisible(true);
					iv.user12.setText(lista.get(i).getNombre());
					iv.user12.setVisible(true);
					iv.estatus12.setText(lista.get(i).getVoto());
					iv.estatus12.setVisible(true);
					iv.btnRe12.setVisible(true);
					if (lista.get(i).getVoto().equals("EN CONTRA")) {
						iv.estatus12.setStyle("-fx-text-fill: #F44336;");
					}
					if (lista.get(i).getVoto().equals("VOTO SALVADO")) {
						iv.estatus12.setStyle("-fx-text-fill: #B2EBF2;");
						
					}
					if (lista.get(i).getVoto().equals("EN BLANCO")) {
						iv.estatus12.setStyle("-fx-text-fill: black;");
					}
				}

				if (lista.size() >= 0) {
					iv.btn_finVoto.setDisable(false);
					iv.btn_reVoto.setDisable(false);
				}

			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void reiniciarVotos() throws IOException {
		try {
			String message = LoginController.servidor.limpiarVoto();
			System.out.println(message);
			data.header = "Votos Reiniciados";
			data.cuerpo = message;
			try {
				// String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg +
				// "'" + "}";

				JSONObject js = new JSONObject();
				js.put("name", "REINICIAR VOTOS");

				String json = js.toJSONString();

				System.out.println("Se envio:" + json);
				PantallaPrincipalCtrl.dos.writeUTF(json);

			} catch (IOException E) {
				E.printStackTrace();
			}
			/*Stage newStage = new Stage();
			AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("VentanaDialogo.fxml"));
			Scene scene = new Scene(pane);
			newStage.setScene(scene);
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.show();*/

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iv.btn_finVoto.setDisable(true);
		iv.btn_reVoto.setDisable(true);
	}
	
	
	@SuppressWarnings("unchecked")
	public void reiniciarcontrol(String user, String voto,int indice ) throws RemoteException {
		limpiar();
    	System.out.println("riniciando xD");
    	LoginController.servidor.reiniciarVoto(user,voto ,indice);
    	
    	try {
    		
            JSONObject js = new JSONObject();
            js.put("name", "REINICIAR1VOTO");
            js.put("message", user);

            String json = js.toJSONString();


            System.out.println("Se envio:"+json);

            PantallaPrincipalCtrl.dos.writeUTF(json);

        } catch(IOException E) {
            E.printStackTrace();
        }
	}
	
	
	
	public void reiniciarVoto() throws RemoteException {
		reiniciarcontrol(iv.user1.getText(),iv.status1.getText(),0);
	}
	
	public void reiniciarVoto2() throws RemoteException {
		reiniciarcontrol(iv.user2.getText(),iv.status2.getText(),1);
	}
	
	public void reiniciarVoto3() throws RemoteException {
		reiniciarcontrol(iv.user3.getText(),iv.status3.getText(),2);
	}
	
	public void reiniciarVoto4() throws RemoteException {
		reiniciarcontrol(iv.user4.getText(),iv.status4.getText(),3);
	}
	
	public void reiniciarVoto5() throws RemoteException {
		reiniciarcontrol(iv.user5.getText(),iv.status5.getText(),4);
	}
	
	public void reiniciarVoto6() throws RemoteException {
		reiniciarcontrol(iv.user6.getText(),iv.status6.getText(),5);
	}
	
	public void reiniciarVoto7() throws RemoteException {
		reiniciarcontrol(iv.user7.getText(),iv.status7.getText(),6);
	}
	
	public void reiniciarVoto8() throws RemoteException {
		reiniciarcontrol(iv.user8.getText(),iv.status8.getText(),7);
	}
	
	
	public void reiniciarVoto9() throws RemoteException {
		reiniciarcontrol(iv.user9.getText(),iv.status9.getText(),8);
	}
	
	public void reiniciarVoto10() throws RemoteException {
		reiniciarcontrol(iv.user10.getText(),iv.status10.getText(),9);
	}
	
	public void reiniciarVoto11() throws RemoteException {
		reiniciarcontrol(iv.user11.getText(),iv.status11.getText(),10);
	}
	
	public void reiniciarVoto12() throws RemoteException {
		reiniciarcontrol(iv.user12.getText(),iv.estatus12.getText(),11);
	}
	
	// meotodo para convertir la la imagen
	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		Image img = new Image(bis);
		return img;
	}
	
	

	@SuppressWarnings("unchecked")
	public void iniciarVotacion() {
		try {

			// String json = "{" + " 'name' : '" + data.name + "', 'message' : '" + msg +
			// "'" + "}";

			JSONObject js = new JSONObject();
			js.put("name", "InicioVoto");

			String json = js.toJSONString();

			System.out.println("Se envio:" + json);

			PantallaPrincipalCtrl.dos.writeUTF(json);

		} catch (IOException E) {
			E.printStackTrace();
		}

	}
	
	public void guardarVotos(int id_ordendia) {
		try {
			System.out.println(VotoResumen.si);
			String estado = LoginController.servidor.guardarVotos(id_ordendia, VotoResumen.si, VotoResumen.no, VotoResumen.blanco, VotoResumen.salvo);
			//guardamos la variable estado
			VotoResumen.estado=estado;
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}
	
}
