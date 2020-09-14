package co.sistemas.transaccionales.uniminuto.repository;
import co.sistemas.transaccionales.uniminuto.domain.Vuelos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vuelos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VuelosRepository extends JpaRepository<Vuelos, Long> {

}
