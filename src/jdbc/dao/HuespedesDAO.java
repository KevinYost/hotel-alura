package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
}
