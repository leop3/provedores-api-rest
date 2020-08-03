package lepo.provedoresapirest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lepo.provedoresapirest.entities.DocumentoEntity;

@Repository
public interface DocumentoRepository extends CrudRepository<DocumentoEntity, Long> {

	public List<DocumentoEntity> findByProveedorId(Long proveedorId);

	public DocumentoEntity findByIdAndFechaDeleteIsNull(Long id);

	public DocumentoEntity findByIdAndFechaDeleteIsNullAndEstaPagadaIsFalse(Long id);

	public List<DocumentoEntity> findByProveedorIdAndFechaDeleteIsNullOrderByFechaInsertDesc(Long proveedorId);
}
