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
import jdbc.modelo.Huespedes;


public class HuespedesDAO {
	
	final private Connection con;
	
	public HuespedesDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Huespedes nuevoHuesped) {
		try(con){
			final java.sql.PreparedStatement statement = con.prepareStatement("INSERT INTO HUESPEDES (nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva) "
					+ " VALUES (?, ?, ?, ?, ?, ?)",
					java.sql.Statement.RETURN_GENERATED_KEYS);
			try(statement){
					ejecutaRegistro(nuevoHuesped, statement);
					}
			}
			catch (SQLException e) {
					throw new RuntimeException(e);
			}
	}

	
	
	private void ejecutaRegistro(Huespedes nuevoHuesped, java.sql.PreparedStatement statement)throws SQLException {
		statement.setString(1,nuevoHuesped.getNombre());
        statement.setString(2,nuevoHuesped.getApellido());
        statement.setDate(3,nuevoHuesped.getFechaNacimiento());
        statement.setString(4, nuevoHuesped.getNacionalidad());
        statement.setInt(5, nuevoHuesped.getTelefono());
        statement.setInt(6, nuevoHuesped.getIdReserva());
		
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		
		try(resultSet){
		
		while(resultSet.next()) {
			nuevoHuesped.setId(resultSet.getInt(1));
			
				System.out.println(
						String.format("El huesped fue cargado", nuevoHuesped));
			}
		}
	}

	public Object modificar(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, Integer telefono,
			Integer idReserva) {
		try {
	        final PreparedStatement statement = con.prepareStatement(
	                "UPDATE HUESPEDES SET "
	                + " NOMBRE = ?,"
	                + " APELLIDO = ?,"
	                + " FECHANACIMIENTO = ?,"
	                + " NACIONALIDAD = ?,"
	                + " TELEFONO = ?,"
	                + " IDRESERVA = ?"
	                + " WHERE ID = ?");

	        try (statement) {
	            statement.setString(1, nombre);
	            statement.setString(2, apellido);
	            statement.setDate(3, fechaNacimiento);
	            statement.setString(4, nacionalidad);
	            statement.setInt(5, telefono);
	            statement.setInt(6, idReserva);
	            statement.setInt(7, id);

	            
	            statement.execute();

	            int updateCount = statement.getUpdateCount();

	            return updateCount;
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

	public List<Map<String, String>> listarHuespedes() {
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

	public List<Map<String, String>> buscarHuespedes(String whereHuesped) {
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
}
