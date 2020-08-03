package lepo.provedoresapirest.controllers;

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

import lepo.provedoresapirest.entities.UsuarioEntity;
import lepo.provedoresapirest.reponse.Response;
import lepo.provedoresapirest.reponse.UsuarioResponse;
import lepo.provedoresapirest.repositories.UsuarioRepository;
import lepo.provedoresapirest.request.CambiarContraseñaUsuarioRequest;
import lepo.provedoresapirest.request.NuevoUsuarioRequest;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepo;

	@GetMapping
	public ResponseEntity<Boolean> validarUsuario(@RequestParam String usuario, String contraseña) {

		UsuarioEntity user = usuarioRepo.findByUsuarioAndContraseñaAndHabilitado(usuario, contraseña, true);

		Boolean usuarioEncontrado = user != null;

		return new ResponseEntity<>(usuarioEncontrado, usuarioEncontrado ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping("/datos")
	public ResponseEntity<UsuarioResponse> getUsuariosByUsuario(@RequestParam String usuario) {

		UsuarioResponse response = new UsuarioResponse();
		try {
			List<UsuarioEntity> users = usuarioRepo.findByUsuario(usuario);
			if (!users.isEmpty()) {
				response.setUsuarios(users);
				response.setMensaje("Se encontraron usuarios");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setMensaje("No se ha encontrado usuarios.");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<Response> crearNuevoUsuario(@RequestBody NuevoUsuarioRequest nuevoUsuario) {

		Response response = new Response();

		try {
			List<UsuarioEntity> usersEncontrados = usuarioRepo.findByUsuario(nuevoUsuario.getUsuario());

			if (usersEncontrados.isEmpty()) {
				UsuarioEntity user = new UsuarioEntity(nuevoUsuario.getUsuario(), nuevoUsuario.getContraseña());

				usuarioRepo.save(user);
				response.setMensaje("Se ha generado el usuario correctamente.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setMensaje("nombre de usuario no disponible.");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping
	public ResponseEntity<Response> borrarUsuario(@RequestParam Long id) {

		Response response = new Response();

		try {
			UsuarioEntity user = usuarioRepo.findById(id).orElse(null);

			if (user != null) {
				user.setHabilitado(false);
				usuarioRepo.save(user);
				response.setMensaje("Se ha deshabilitado el usuario correctamente.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setMensaje("El usuario no existe.");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PatchMapping
	public ResponseEntity<Response> cambiarContraseñaUsuario(@RequestBody CambiarContraseñaUsuarioRequest request) {

		Response response = new Response();

		try {
			UsuarioEntity user = usuarioRepo.findById(request.getId()).orElse(null);

			if (user != null) {
				user.setContraseña(request.getNuevaContraseña());
				usuarioRepo.save(user);
				response.setMensaje("Se ha actualizado la contraseña del usuario correctamente.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setMensaje("El usuario no existe.");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMensaje("Se produjo un error interno.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
