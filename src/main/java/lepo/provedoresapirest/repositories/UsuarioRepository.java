package lepo.provedoresapirest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lepo.provedoresapirest.entities.UsuarioEntity;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Long> {

	public UsuarioEntity findByUsuarioAndContraseñaAndHabilitado(String usuario, String contraseña, boolean b);

	public List<UsuarioEntity> findByUsuario(String usuario);

}
