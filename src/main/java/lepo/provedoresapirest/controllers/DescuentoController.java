package lepo.provedoresapirest.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lepo.provedoresapirest.entities.DescuentoEntity;
import lepo.provedoresapirest.entities.ProveedorEntity;
import lepo.provedoresapirest.reponse.DescuentoResponse;
import lepo.provedoresapirest.repositories.DescuentoRepository;
import lepo.provedoresapirest.repositories.ProveedorRepository;
import lepo.provedoresapirest.request.DescuentoRequest;

@RestController
@RequestMapping(path = "/proveedor/descuento")
public class DescuentoController {

	@Autowired
	ProveedorRepository proveedorRepo;

	@Autowired
	DescuentoRepository descuentoRepo;

	@GetMapping
	public ResponseEntity<DescuentoResponse> getDescuentoByProveedor(@RequestParam Long id) {

		DescuentoResponse response = new DescuentoResponse();

		try {

			ProveedorEntity proveedor = proveedorRepo.findById(id).orElse(null);

			if (proveedor != null) {
				DescuentoEntity descuento = descuentoRepo.findByProveedorId(id);
				if (descuento != null) {

					response.setDescuento(descuento);
					response.setMensaje("Se encontró descuento.");
					return new ResponseEntity<DescuentoResponse>(response, HttpStatus.OK);
				} else {
					response.setMensaje("No se ha encontrado descuento para el proveedor.");
				}

			} else {
				response.setMensaje("No se ha encontrado el proveedor.");
			}
			return new ResponseEntity<DescuentoResponse>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<DescuentoResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<DescuentoResponse> getDescuentoByProveedor(@RequestBody DescuentoRequest descuento) {

		DescuentoResponse response = new DescuentoResponse();

		try {
			ProveedorEntity proveedor = proveedorRepo.findById(descuento.getProveedorId()).orElse(null);
			if (proveedor != null) {
				DescuentoEntity descuentoEncontrado = descuentoRepo.findByProveedorId(descuento.getProveedorId());
				if (descuentoEncontrado == null) {
					DescuentoEntity nuevoDescuento = new DescuentoEntity(descuento.getPorcentaje(), proveedor);
					descuentoRepo.save(nuevoDescuento);
					response.setMensaje("Se ha registrado el descuento correctamente.");
					return new ResponseEntity<DescuentoResponse>(response, HttpStatus.OK);
				} else {
					if (descuentoEncontrado.getFechaDelete() != null) {
						descuentoEncontrado.setFechaDelete(null);
						descuentoEncontrado.setFechaActualizacion(new Date());
						descuentoEncontrado.setPorcentaje(descuento.getPorcentaje());
						descuentoRepo.save(descuentoEncontrado);
						response.setMensaje("Se ha registrado el descuento correctamente.");
						return new ResponseEntity<DescuentoResponse>(response, HttpStatus.OK);
					} else {
						response.setMensaje("Ya tiene registrado un descuento.");
					}
				}
			} else {
				response.setMensaje("No se ha encontrado el proveedor.");
			}
			return new ResponseEntity<DescuentoResponse>(response, HttpStatus.BAD_REQUEST);
		} catch (

		Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<DescuentoResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<DescuentoResponse> borrarDescuentoById(@RequestParam Long id) {

		DescuentoResponse response = new DescuentoResponse();

		try {
			DescuentoEntity descuentoEncontrado = descuentoRepo.findById(id).orElse(null);
			if (descuentoEncontrado != null) {
				descuentoEncontrado.setFechaDelete(new Date());
				descuentoRepo.save(descuentoEncontrado);
				response.setMensaje("Se ha eliminado el descuento correctamente.");
				return new ResponseEntity<DescuentoResponse>(response, HttpStatus.OK);
			} else {
				response.setMensaje("No existe el descuento con el id proporcionado.");
				return new ResponseEntity<DescuentoResponse>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<DescuentoResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
