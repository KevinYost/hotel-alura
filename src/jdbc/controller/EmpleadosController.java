package jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdbc.dao.EmpleadosDAO;
import jdbc.factory.ConnectionFactory;

public class EmpleadosController {

	private EmpleadosDAO empleadosDAO;
	
	public EmpleadosController() {
		this.empleadosDAO = new EmpleadosDAO(new ConnectionFactory().recuperaConexion());
	}

	public List<String> verificarUsuario() {
		ConnectionFactory factory = new ConnectionFactory();
		try{
			final Connection con = factory.recuperaConexion();
			try (con) {
				final PreparedStatement statement = con
						.prepareStatement("SELECT usuario FROM usuarios");
				try (statement) {
					statement.execute();
					
					ResultSet resultSet = statement.getResultSet();
					
					List<String> usuarios = new ArrayList<>();
					
					while(resultSet.next()) {
						
						String usuario = resultSet.getString("usuario");
						usuarios.add(usuario);	
				
					}
					return usuarios;

					}
				}
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
	}
	
	public List<String> verificarClave() {
		ConnectionFactory factory = new ConnectionFactory();
		try{
			final Connection con = factory.recuperaConexion();
			try (con) {
				final PreparedStatement statement = con
						.prepareStatement("SELECT clave FROM usuarios");
				try (statement) {
					statement.execute();
					
					ResultSet resultSet = statement.getResultSet();

					List<String> claves = new ArrayList<>();
					while(resultSet.next()) {
						String clave = resultSet.getString("clave");
						claves.add(clave);					
					}
					return claves;
					}
				}
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
	}

}
