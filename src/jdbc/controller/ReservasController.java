package jdbc.controller;


import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservasController {
	
	private ReservaDAO reservaDAO;
	
	public ReservasController() {
		this.reservaDAO = new ReservaDAO(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Reserva nuevaReserva) {
		reservaDAO.guardar(nuevaReserva);
	}
}
