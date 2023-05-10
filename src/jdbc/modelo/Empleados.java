package jdbc.modelo;

public class Empleados {

	private String usuario;
	private String clave;
	
	
	
	
	public Empleados(String usuario, String clave) {
		this.usuario = usuario;
		this.clave = clave;
	}




	public String getUsuario() {
		return usuario;
	}




	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}




	public String getClave() {
		return clave;
	}




	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
}
