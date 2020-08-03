package lepo.provedoresapirest.request;

public class CambiarContraseñaUsuarioRequest {

	Long id;

	String nuevaContraseña;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNuevaContraseña() {
		return nuevaContraseña;
	}

	public void setNuevaContraseña(String nuevaContraseña) {
		this.nuevaContraseña = nuevaContraseña;
	}

}
