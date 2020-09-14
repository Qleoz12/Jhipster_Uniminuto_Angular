package co.sistemas.transaccionales.uniminuto.repository;
import co.sistemas.transaccionales.uniminuto.domain.AvionModelos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AvionModelos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvionModelosRepository extends JpaRepository<AvionModelos, Long> {

}
