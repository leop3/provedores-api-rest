package lepo.provedoresapirest.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lepo.provedoresapirest.entities.DocumentoEntity;
import lepo.provedoresapirest.entities.ProveedorEntity;
import lepo.provedoresapirest.reponse.ProveedorResponse;
import lepo.provedoresapirest.reponse.Response;
import lepo.provedoresapirest.reponse.SaldoResponse;
import lepo.provedoresapirest.repositories.DescuentoRepository;
import lepo.provedoresapirest.repositories.DocumentoRepository;
import lepo.provedoresapirest.repositories.ProveedorRepository;

@RestController
@RequestMapping(path = "/proveedor")
public class ProveedorController {

	@Autowired
	ProveedorRepository proveedorRepo;

	@Autowired
	DescuentoRepository descuentoRepo;

	@Autowired
	DocumentoRepository documentoRepo;

	@GetMapping
	public ResponseEntity<ProveedorResponse> getProveedores() {

		ProveedorResponse response = new ProveedorResponse();

		try {
			List<ProveedorEntity> proveedores = (List<ProveedorEntity>) proveedorRepo.findAll();

			response.setMensaje(
					proveedores.isEmpty() ? "No se ha encontrado ningun proveedor." : "Se encontraron elementos");

			response.setProveedores(proveedores);

			return new ResponseEntity<ProveedorResponse>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<ProveedorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public ResponseEntity<ProveedorResponse> getProveedorById(@RequestParam Long proveedorId) {

		ProveedorResponse response = new ProveedorResponse();

		try {
			ProveedorEntity proveedor = (ProveedorEntity) proveedorRepo.findById(proveedorId).orElse(null);

			response.setMensaje(proveedor == null ? "No se encontró el proveedor." : "Se encontró el proveedor.");

			response.setProveedor(proveedor);

			return new ResponseEntity<ProveedorResponse>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<ProveedorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<Response> createProveedor(@RequestBody ProveedorEntity nuevoProveedor) {

		Response response = new Response();

		try {
			nuevoProveedor.setFechaInsert(new Date());
			proveedorRepo.save(nuevoProveedor);

			response.setMensaje("Se ha grabado correctamente.");

			return new ResponseEntity<Response>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<Response> modificarProveedor(@RequestBody ProveedorEntity nuevoProveedor) {

		Response response = new Response();

		try {
			Boolean existeProveedor = proveedorRepo.existsById(nuevoProveedor.getId());

			if (existeProveedor) {
				nuevoProveedor.setFechaActualizacion(new Date());
				proveedorRepo.save(nuevoProveedor);

				response.setMensaje("Se ha actualizado correctamente.");

				return new ResponseEntity<Response>(response, HttpStatus.OK);
			} else {
				response.setMensaje("No se ha encontrado el proveedor.");
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<Response> borrarProveedor(@RequestParam Long id) {

		Response response = new Response();

		try {
			ProveedorEntity proveedor = proveedorRepo.findById(id).orElse(null);

			if (proveedor != null) {
				proveedor.setFechaDelete(new Date());
				proveedorRepo.save(proveedor);

				response.setMensaje("Se ha eliminado correctamente.");

				return new ResponseEntity<Response>(response, HttpStatus.OK);
			} else {
				response.setMensaje("No se ha encontrado el proveedor.");
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping
	public ResponseEntity<Response> habilitarProveedor(@RequestParam Long id) {

		Response response = new Response();

		try {
			ProveedorEntity proveedor = proveedorRepo.findById(id).orElse(null);

			if (proveedor != null) {

				if (proveedor.getFechaDelete() != null) {
					proveedor.setFechaDelete(null);
					proveedor.setFechaActualizacion(new Date());
					proveedorRepo.save(proveedor);

					response.setMensaje("Se ha habilitado correctamente.");

					return new ResponseEntity<Response>(response, HttpStatus.OK);
				} else {
					response.setMensaje("El proveedor se encuentra habilitado.");
				}

			} else {
				response.setMensaje("No se ha encontrado el proveedor.");
			}
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/saldo")
	public ResponseEntity<SaldoResponse> getSaldoByProveedorId(@RequestParam Long id) {

		SaldoResponse response = new SaldoResponse();

		try {
			Double monto = buscarMontoTotalDelProveedor(id);
			response.setSaldo(monto);

			response.setMensaje(monto == null ? "No tiene facturas." : "Se encontró el siguiente saldo.");
			
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private Double buscarMontoTotalDelProveedor(Long proveedorId) {
		try {
			List<DocumentoEntity> documentos = documentoRepo
					.findByProveedorIdAndFechaDeleteIsNullOrderByFechaInsertDesc(proveedorId);

			if (documentos != null) {

				List<Double> montos = documentos.stream()
						.map(doc -> doc.getEstaPagada() ? new Double(0)
								: doc.getTipoDocumento().getId().equals(2) ? doc.getMonto() * (-1) : doc.getMonto())
						.collect(Collectors.toList());

				Double montoTotal = new Double(0);

				for (Double monto : montos) {
					montoTotal += monto;
				}
				return montoTotal;
			}

			return null;

		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
