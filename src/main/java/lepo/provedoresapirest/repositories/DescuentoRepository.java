package lepo.provedoresapirest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lepo.provedoresapirest.entities.DescuentoEntity;

@Repository
public interface DescuentoRepository extends CrudRepository<DescuentoEntity, Long> {

}
