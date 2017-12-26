package clases;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import application.LoginController;
import application.QuorumCtrl;
import gad.manta.common.Asistencia;
import gad.manta.common.Conexion;
import gad.manta.common.Usuario;
import javafx.scene.image.Image;

public class TramQuorum {
	Conexion conexion;
	public static QuorumCtrl q;

	// metodos para los diferentes cotroladores
	public void tramiteQuorum() {
		try {

			// lista en rmi
			List<Usuario> lista = LoginController.servidor.consultaQuorum();

			q.lblpresentes.setText(String.valueOf(lista.size()));
			q.lblausentes.setText(String.valueOf(12 - lista.size()));
			conexion = new Conexion();
			conexion.establecerConexion();
			Asistencia asistencia;
			for (int i = 0; i < lista.size(); i++) {
				if (i == 0) {
					q.img1.setVisible(true);
					q.img1.setImage(convertirImg(lista.get(i).getImg()));
					q.user1.setVisible(true);
					q.user1.setText(lista.get(i).getNombre());
					q.status1.setText(lista.get(i).getStatus());
					q.status1.setVisible(true);
					asistencia = new Asistencia(lista.get(i).getId());
					asistencia.tomarAsistencia(conexion.getConnection());
				}

				if (i == 1) {
					q.img2.setImage(convertirImg(lista.get(i).getImg()));
					q.img2.setVisible(true);
					q.user2.setText(lista.get(i).getNombre());
					q.user2.setVisible(true);
					q.status2.setText(lista.get(i).getStatus());
					q.status2.setVisible(true);
					asistencia = new Asistencia(lista.get(1).getId());
					asistencia.tomarAsistencia(conexion.getConnection());

				}

				if (i == 2) {
					q.img3.setImage(convertirImg(lista.get(i).getImg()));
					q.img3.setVisible(true);
					q.user3.setText(lista.get(i).getNombre());
					q.user3.setVisible(true);
					q.status3.setText(lista.get(i).getStatus());
					q.status3.setVisible(true);
					asistencia = new Asistencia(lista.get(2).getId());
					asistencia.tomarAsistencia(conexion.getConnection());

				}
				if (i == 3) {
					q.img4.setImage(convertirImg(lista.get(i).getImg()));
					q.img4.setVisible(true);
					q.user4.setText(lista.get(i).getNombre());
					q.user4.setVisible(true);
					q.status4.setText(lista.get(i).getStatus());
					q.status4.setVisible(true);
					asistencia = new Asistencia(lista.get(i).getId());
					asistencia.tomarAsistencia(conexion.getConnection());

				}
				if (i == 4) {
					q.img5.setImage(convertirImg(lista.get(i).getImg()));
					q.img5.setVisible(true);
					q.user5.setText(lista.get(i).getNombre());
					q.user5.setVisible(true);
					q.status5.setText(lista.get(i).getStatus());
					q.status5.setVisible(true);
					asistencia = new Asistencia(lista.get(4).getId());
					asistencia.tomarAsistencia(conexion.getConnection());
				}
				if (i == 5) {
					q.img6.setImage(convertirImg(lista.get(i).getImg()));
					q.img6.setVisible(true);
					q.user6.setText(lista.get(i).getNombre());
					q.user6.setVisible(true);
					q.status6.setText(lista.get(i).getStatus());
					q.status6.setVisible(true);
					asistencia = new Asistencia(lista.get(i).getId());
					asistencia.tomarAsistencia(conexion.getConnection());
				}
				if (i == 6) {
					q.img7.setImage(convertirImg(lista.get(i).getImg()));
					q.img7.setVisible(true);
					q.user7.setText(lista.get(i).getNombre());
					q.user7.setVisible(true);
					q.status7.setText(lista.get(i).getStatus());
					q.status7.setVisible(true);
					asistencia = new Asistencia(lista.get(i).getId());
					asistencia.tomarAsistencia(conexion.getConnection());
				}
				if (i == 7) {
					q.img8.setImage(convertirImg(lista.get(i).getImg()));
					q.img8.setVisible(true);
					q.user8.setText(lista.get(i).getNombre());
					q.user8.setVisible(true);
					q.status8.setText(lista.get(i).getStatus());
					q.status8.setVisible(true);
					asistencia = new Asistencia(lista.get(i).getId());
					asistencia.tomarAsistencia(conexion.getConnection());
				}
				if (i == 8) {
					q.img9.setImage(convertirImg(lista.get(i).getImg()));
					q.img9.setVisible(true);
					q.user9.setText(lista.get(i).getNombre());
					q.user9.setVisible(true);
					q.status9.setText(lista.get(i).getStatus());
					q.status9.setVisible(true);
					asistencia = new Asistencia(lista.get(i).getId());
					asistencia.tomarAsistencia(conexion.getConnection());
				}
				if (i == 9) {
					q.img10.setImage(convertirImg(lista.get(i).getImg()));
					q.img10.setVisible(true);
					q.user10.setText(lista.get(i).getNombre());
					q.user10.setVisible(true);
					q.status10.setText(lista.get(i).getStatus());
					q.status10.setVisible(true);
					asistencia = new Asistencia(lista.get(i).getId());
					asistencia.tomarAsistencia(conexion.getConnection());
				}
				if (i == 10) {
					q.img11.setImage(convertirImg(lista.get(i).getImg()));
					q.img11.setVisible(true);
					q.user11.setText(lista.get(i).getNombre());
					q.user11.setVisible(true);
					q.status11.setText(lista.get(i).getStatus());
					q.status11.setVisible(true);
					asistencia = new Asistencia(lista.get(i).getId());
					asistencia.tomarAsistencia(conexion.getConnection());
				}
				if (i == 11) {
					q.img12.setImage(convertirImg(lista.get(i).getImg()));
					q.img12.setVisible(true);
					q.user12.setText(lista.get(i).getNombre());
					q.user12.setVisible(true);
					q.estatus12.setText(lista.get(i).getStatus());
					q.estatus12.setVisible(true);
					asistencia = new Asistencia(lista.get(i).getId());
					asistencia.tomarAsistencia(conexion.getConnection());
				}

				if (lista.size() >= 2) {
					q.btn_finAsistencia.setDisable(false);
					q.txtCumple.setText("Cumple con el mínimo de miembros para inicar la sesión");
				}
			}
			conexion.cerrarConexion();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// meotodo para convertir la la imagen
	public Image convertirImg(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

		Image img = new Image(bis);
		return img;
	}
}
