package lepo.provedoresapirest.reponse;

import java.util.List;

import lepo.provedoresapirest.entities.UsuarioEntity;

public class UsuarioResponse extends Response {

	List<UsuarioEntity> usuarios;

	public List<UsuarioEntity> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioEntity> usuarios) {
		this.usuarios = usuarios;
	}

}
