package lepo.provedoresapirest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lepo.provedoresapirest.entities.DescuentoEntity;
import lepo.provedoresapirest.entities.DocumentoEntity;
import lepo.provedoresapirest.entities.ProveedorEntity;
import lepo.provedoresapirest.entities.TipoDocumentoEntity;
import lepo.provedoresapirest.reponse.DocumentoResponse;
import lepo.provedoresapirest.repositories.DescuentoRepository;
import lepo.provedoresapirest.repositories.DocumentoRepository;
import lepo.provedoresapirest.repositories.ProveedorRepository;
import lepo.provedoresapirest.repositories.TipoDocumentoRepository;
import lepo.provedoresapirest.request.DocumentoRequest;

@RestController
@RequestMapping(path = "/proveedor/documento")
public class DocumentoController {

	@Autowired
	DocumentoRepository documentoRepo;

	@Autowired
	TipoDocumentoRepository tipoDocumentoRepo;

	@Autowired
	ProveedorRepository proveedorRepo;

	@Autowired
	DescuentoRepository descuentoRepo;

	@GetMapping
	public ResponseEntity<DocumentoResponse> getDocumentosByProveedor(@RequestParam Long id) {

		DocumentoResponse response = new DocumentoResponse();

		try {
			if (proveedorRepo.existsById(id)) {
				List<DocumentoEntity> documentos = documentoRepo.findByProveedorId(id);

				if (!documentos.isEmpty()) {
					response.setDocumentos(documentos);
					response.setMensaje("Se han encontrado documentos");
				} else {
					response.setMensaje("No se han encontrado documentos.");
				}
				return new ResponseEntity<DocumentoResponse>(response, HttpStatus.OK);
			} else {
				response.setMensaje("El proveedor proporcionado no existe.");
				return new ResponseEntity<DocumentoResponse>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<DocumentoResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<DocumentoResponse> nuevoDocumento(@RequestBody DocumentoRequest documento) {

		DocumentoResponse response = new DocumentoResponse();

		try {

			ProveedorEntity proveedor = proveedorRepo.findById(documento.getProveedorId()).orElse(null);
			TipoDocumentoEntity tipoDocumento = tipoDocumentoRepo.findById(documento.getTipoDocumentoId()).orElse(null);

			if (proveedor != null) {
				if (tipoDocumento != null) {

					DocumentoEntity nuevoDocumento = new DocumentoEntity(tipoDocumento, proveedor, documento.getMonto(),
							documento.getNumeroFactura(), documento.getFechaDocumento(), new Date());

					documentoRepo.save(nuevoDocumento);

					response.setMensaje("Se genero el documento de forma correcta.");
					return new ResponseEntity<DocumentoResponse>(response, HttpStatus.OK);
				} else {
					response.setMensaje("No existe el tipo de documento");
				}
			} else {
				response.setMensaje("No existe el proveedor proporcionado.");
			}
			return new ResponseEntity<DocumentoResponse>(response, HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<DocumentoResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<DocumentoResponse> anularDocumentoById(@RequestParam Long id) {

		DocumentoResponse response = new DocumentoResponse();

		try {

			DocumentoEntity documento = (DocumentoEntity) documentoRepo
					.findByIdAndFechaDeleteIsNullAndEstaPagadaIsFalse(id);

			if (documento != null) {
				documento.setFechaDelete(new Date());
				documentoRepo.save(documento);
				response.setMensaje("Se ha eliminado el documento correctamente.");
				return new ResponseEntity<DocumentoResponse>(response, HttpStatus.OK);
			} else {
				response.setMensaje("No existe el documento ,está anulado o está pagada.");
				return new ResponseEntity<DocumentoResponse>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<DocumentoResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping
	public ResponseEntity<DocumentoResponse> pagarDocumentoById(@RequestParam Long id) {

		DocumentoResponse response = new DocumentoResponse();

		try {
			DocumentoEntity documento = (DocumentoEntity) documentoRepo
					.findByIdAndFechaDeleteIsNullAndEstaPagadaIsFalse(id);

			if (documento != null) {

				DescuentoEntity descuento = descuentoRepo
						.findByProveedorIdAndFechaDeleteIsNull(documento.getProveedor().getId());

				if (descuento != null) {
					Double oldAmount = documento.getMonto();
					Double newAmount = oldAmount * descuento.getPorcentaje() / 100;
					documento.setMonto(newAmount);
				}
				documento.setFechaPagada(new Date());
				documento.setEstaPagada(true);
				documentoRepo.save(documento);
				response.setMensaje("Se ha realizado el pago del documento correctamente.");
				return new ResponseEntity<DocumentoResponse>(response, HttpStatus.OK);
			} else {
				response.setMensaje("No existe el documento , está anulado o está pagada.");
				return new ResponseEntity<DocumentoResponse>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<DocumentoResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
