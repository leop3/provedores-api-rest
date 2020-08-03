package lepo.provedoresapirest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lepo.provedoresapirest.entities.TipoDocumentoEntity;

@Repository
public interface TipoDocumentoRepository extends CrudRepository<TipoDocumentoEntity, Long> {

}
