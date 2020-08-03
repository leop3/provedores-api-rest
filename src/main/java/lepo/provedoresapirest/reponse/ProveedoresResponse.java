package lepo.provedoresapirest.reponse;

import java.util.List;

import lepo.provedoresapirest.entities.ProveedorEntity;

public class ProveedoresResponse extends Response {

	List<ProveedorEntity> proveedores;

	public List<ProveedorEntity> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<ProveedorEntity> proveedores) {
		this.proveedores = proveedores;
	}

}
