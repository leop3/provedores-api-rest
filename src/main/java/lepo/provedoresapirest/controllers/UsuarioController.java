package lepo.provedoresapirest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lepo.provedoresapirest.entities.UsuarioEntity;
import lepo.provedoresapirest.repositories.UsuarioRepository;

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
}
