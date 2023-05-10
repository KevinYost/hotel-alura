package jdbc.controller;

import jdbc.dao.HuespedesDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;

public class HuespedesController {
	
	
	private HuespedesDAO huespedesDAO;
	
	public HuespedesController() {
		this.huespedesDAO = new HuespedesDAO(new ConnectionFactory().recuperaConexion());
	}

	public void guardar(Huespedes nuevoHuesped) {
		huespedesDAO.guardar(nuevoHuesped);
	}

}
