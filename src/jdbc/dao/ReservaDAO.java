package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public int eliminarHuesped(Integer id) {	
	    try {
	        final PreparedStatement statement = con.prepareStatement("DELETE FROM HUESPEDES WHERE ID = ?");
	        

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
}
