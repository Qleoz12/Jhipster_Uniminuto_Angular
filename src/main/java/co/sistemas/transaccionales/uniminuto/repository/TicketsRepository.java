package co.sistemas.transaccionales.uniminuto.repository;
import co.sistemas.transaccionales.uniminuto.domain.Tickets;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tickets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {

}
