package lepo.provedoresapirest.reponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lepo.provedoresapirest.entities.ProveedorEntity;

public class ProveedorResponse extends Response {

	@JsonInclude(Include.NON_EMPTY)
	List<ProveedorEntity> proveedores;

	@JsonInclude(Include.NON_NULL)
	ProveedorEntity proveedor;

	public List<ProveedorEntity> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<ProveedorEntity> proveedores) {
		this.proveedores = proveedores;
	}

	public ProveedorEntity getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedorEntity proveedor) {
		this.proveedor = proveedor;
	}

}
