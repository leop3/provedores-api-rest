package lepo.provedoresapirest.reponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lepo.provedoresapirest.entities.DocumentoEntity;

public class DocumentoResponse extends Response {

	@JsonInclude(Include.NON_NULL)
	List<DocumentoEntity> documentos;

	public List<DocumentoEntity> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocumentoEntity> documentos) {
		this.documentos = documentos;
	}

}
