package jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdbc.dao.HuespedesDAO;
import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;



public class BusquedaController {
	
	private ReservaDAO reservaDAO;
	private HuespedesDAO huespedesDAO;
	
	
	public BusquedaController() {
		this.reservaDAO = new ReservaDAO(new ConnectionFactory().recuperaConexion());
		this.huespedesDAO = new HuespedesDAO(new ConnectionFactory().recuperaConexion());
	}
	
	
	
	public List<Map<String, String>> listarReservas(){
		return reservaDAO.listarReservas();
	}
	
	public List<Map<String, String>> listarHuespedes(){
		return huespedesDAO.listarHuespedes();
	}
	
	
	
	public int eliminarReservas(Integer id){
		return reservaDAO.eliminarReservas(id);
	}
	
	public int eliminarHuesped(Integer id){
		return huespedesDAO.eliminarHuesped(id);
	}


	
	
	public  List<Map<String, String>> buscarReservas(String where){
		return reservaDAO.buscarReservas(where);
	}
	
	public List<Map<String, String>> buscarHuespedes(String whereHuesped){
		return huespedesDAO.buscarHuespedes(whereHuesped);
	}

	
	
	
	
	public Object modificarReservas(Integer id, Date fechaEntrada, Date fechaSalida, String valor, String formaPago) {
		return reservaDAO.modificar(id, fechaEntrada, fechaSalida, valor, formaPago);
	}


	public Object modificarHuespedes(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, Integer telefono,
			Integer idReserva) {
		return huespedesDAO.modificar(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
		
	}
}
