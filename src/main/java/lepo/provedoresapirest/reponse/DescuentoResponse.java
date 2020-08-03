package lepo.provedoresapirest.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lepo.provedoresapirest.entities.DescuentoEntity;

public class DescuentoResponse extends Response {

	@JsonInclude(Include.NON_NULL)
	DescuentoEntity descuento;

	public DescuentoEntity getDescuento() {
		return descuento;
	}

	public void setDescuento(DescuentoEntity descuento) {
		this.descuento = descuento;
	}

}
