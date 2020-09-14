package co.sistemas.transaccionales.uniminuto.repository;
import co.sistemas.transaccionales.uniminuto.domain.Aeropuertos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Aeropuertos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AeropuertosRepository extends JpaRepository<Aeropuertos, Long> {

}
