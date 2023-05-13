package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservaDAO {

	final private Connection con;
	
	public ReservaDAO(Connection con) {
		this.con = con;
	}

	
	public void guardar(Reserva nuevaReserva){	
		try(con){
			final java.sql.PreparedStatement statement = con.prepareStatement("INSERT INTO RESERVAS (fechaentrada, fechasalida, valor, formapago) "
					+ " VALUES (?, ?, ?, ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);
			try(statement){
					ejecutaRegistro(nuevaReserva, statement);
					}
			}
			catch (SQLException e) {
					throw new RuntimeException(e);
			}
    	}
	
	
	private void ejecutaRegistro(Reserva reserva, java.sql.PreparedStatement statement)throws SQLException {
		statement.setDate(1,reserva.getFechaEntrada());
        statement.setDate(2,reserva.getFechaSalida());
        statement.setString(3,reserva.getValor());
        statement.setString(4, reserva.getFormaPago());
		
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		
		try(resultSet){
		
		while(resultSet.next()) {
			reserva.setId(resultSet.getInt(1));
			
				System.out.println(
						String.format("Fue insertado el producto de ID: %s", reserva));
			}
		}
	}
	
	public int eliminarReservas(Integer id) {	
	    try {
	        final PreparedStatement statement = con.prepareStatement("DELETE FROM RESERVAS WHERE ID = ?");

	        try (statement) {
	            statement.setInt(1, id);
	            statement.execute();

	            int updateCount = statement.getUpdateCount();

	            return updateCount;
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}


	public Object modificar(Integer id, Date fechaEntrada, Date fechaSalida, String valor, String formaPago) {
		try {
	        final PreparedStatement statement = con.prepareStatement(
	                "UPDATE RESERVAS SET "
	                + " FECHAENTRADA = ?,"
	                + " FECHASALIDA = ?,"
	                + " VALOR = ?,"
	                + " FORMAPAGO = ?"
	                + " WHERE ID = ?");

	        try (statement) {
	            statement.setDate(1, fechaEntrada);
	            statement.setDate(2, fechaSalida);
	            statement.setString(3, valor);
	            statement.setString(4, formaPago);
	            statement.setInt(5, id);
	            
	            statement.execute();

	            int updateCount = statement.getUpdateCount();

	            return updateCount;
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}


	public List<Map<String, String>> listarReservas() {
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


	public List<Map<String, String>> buscarReservas(String where) {
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
}
