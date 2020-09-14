package co.sistemas.transaccionales.uniminuto.repository;
import co.sistemas.transaccionales.uniminuto.domain.Ciudades;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ciudades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiudadesRepository extends JpaRepository<Ciudades, Long> {

}
