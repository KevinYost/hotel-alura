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
		ConnectionFactory factory = new ConnectionFactory();
		try{
			final Connection con = factory.recuperaConexion();
			try (con) {
				final PreparedStatement statement = con
						.prepareStatement("SELECT ID, FechaEntrada, FechaSalida, Valor, FormaPago FROM reservas");
				try (statement) {
					statement.execute();
	
					ResultSet resultSet = statement.getResultSet();
	
					List<Map<String, String>> resultado = new ArrayList<>();
	
					while (resultSet.next()) {
						Map<String, String> fila = new HashMap<>();
						fila.put("ID", String.valueOf(resultSet.getInt("ID")));
						fila.put("FechaEntrada", String.valueOf(resultSet.getDate("FechaEntrada")));
						fila.put("FechaSalida", String.valueOf(resultSet.getDate("FechaSalida")));
						fila.put("Valor", resultSet.getString("Valor"));
						fila.put("FormaPago", resultSet.getString("FormaPago"));
	
						resultado.add(fila);
					}
					return resultado;
				}
			}
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
		
	}
	
	public List<Map<String, String>> listarHuespedes(){
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try (con) {
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, Nombre, Apellido, FechaNacimiento, Nacionalidad, Telefono, idReserva FROM huespedes");
			try (statement) {
				statement.execute();

				ResultSet resultSet = statement.getResultSet();

				List<Map<String, String>> resultado = new ArrayList<>();

				while (resultSet.next()) {
					Map<String, String> fila = new HashMap<>();
					fila.put("ID", String.valueOf(resultSet.getInt("ID")));
					fila.put("Nombre", resultSet.getString("Nombre"));
					fila.put("Apellido", resultSet.getString("Apellido"));
					fila.put("FechaNacimiento", String.valueOf(resultSet.getDate("FechaNacimiento")));
					fila.put("Nacionalidad", resultSet.getString("Nacionalidad"));
					fila.put("Telefono", String.valueOf(resultSet.getInt("Telefono")));
					fila.put("idReserva", String.valueOf(resultSet.getInt("idReserva")));

					resultado.add(fila);
				}
				return resultado;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int eliminarReservas(Integer id){
		
		return reservaDAO.eliminarReservas(id);
		
	}
	

	public int eliminarHuesped(Integer id){
		return reservaDAO.eliminarHuesped(id);
	}


	
	public  List<Map<String, String>> buscarReservas(String where){
		ConnectionFactory factory = new ConnectionFactory();
		try{
			final Connection con = factory.recuperaConexion();
			try (con) {
				final PreparedStatement statement = con
						.prepareStatement("SELECT ID, FechaEntrada, FechaSalida, Valor, FormaPago FROM reservas " + where);
				try (statement) {
					statement.execute();
	
					ResultSet resultSet = statement.getResultSet();
	
					List<Map<String, String>> resultado = new ArrayList<>();
	
					while (resultSet.next()) {
						Map<String, String> fila = new HashMap<>();
						fila.put("ID", String.valueOf(resultSet.getInt("ID")));
						fila.put("FechaEntrada", String.valueOf(resultSet.getDate("FechaEntrada")));
						fila.put("FechaSalida", String.valueOf(resultSet.getDate("FechaSalida")));
						fila.put("Valor", resultSet.getString("Valor"));
						fila.put("FormaPago", resultSet.getString("FormaPago"));
	
						resultado.add(fila);
					}
					return resultado;
				}
			}
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
	}
	
	public List<Map<String, String>> buscarHuespedes(String whereHuesped){
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try (con) {
			final PreparedStatement statement = con
					.prepareStatement("SELECT ID, Nombre, Apellido, FechaNacimiento, Nacionalidad, Telefono, idReserva FROM huespedes " + whereHuesped);
			try (statement) {
				statement.execute();

				ResultSet resultSet = statement.getResultSet();

				List<Map<String, String>> resultado = new ArrayList<>();

				while (resultSet.next()) {
					Map<String, String> fila = new HashMap<>();
					fila.put("ID", String.valueOf(resultSet.getInt("ID")));
					fila.put("Nombre", resultSet.getString("Nombre"));
					fila.put("Apellido", resultSet.getString("Apellido"));
					fila.put("FechaNacimiento", String.valueOf(resultSet.getDate("FechaNacimiento")));
					fila.put("Nacionalidad", resultSet.getString("Nacionalidad"));
					fila.put("Telefono", String.valueOf(resultSet.getInt("Telefono")));
					fila.put("idReserva", String.valueOf(resultSet.getInt("idReserva")));

					resultado.add(fila);
				}
				return resultado;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	
	public Object modificarReservas(Integer id, Date fechaEntrada, Date fechaSalida, String valor, String formaPago) {
		return reservaDAO.modificar(id, fechaEntrada, fechaSalida, valor, formaPago);
	}


	public Object modificarHuespedes(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, Integer telefono,
			Integer idReserva) {
		return huespedesDAO.modificar(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
		
	}
}
