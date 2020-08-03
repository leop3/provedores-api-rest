package lepo.provedoresapirest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lepo.provedoresapirest.entities.ProveedorEntity;

@Repository
public interface ProveedorRepository extends CrudRepository<ProveedorEntity, Long> {

}
