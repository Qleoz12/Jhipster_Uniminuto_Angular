package co.sistemas.transaccionales.uniminuto.repository;
import co.sistemas.transaccionales.uniminuto.domain.Clientes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Clientes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {

}
