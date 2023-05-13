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
		return empleadosDAO.verificarUsuario();
	}
	
	public List<String> verificarClave() {
		return empleadosDAO.verificarClave();
	}

}
