package co.sistemas.transaccionales.uniminuto.repository;
import co.sistemas.transaccionales.uniminuto.domain.Aviones;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Aviones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvionesRepository extends JpaRepository<Aviones, Long> {

}
