package lepo.provedoresapirest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lepo.provedoresapirest.entities.DocumentoEntity;

@Repository
public interface DocumentoRepository extends CrudRepository<DocumentoEntity, Long> {

}
