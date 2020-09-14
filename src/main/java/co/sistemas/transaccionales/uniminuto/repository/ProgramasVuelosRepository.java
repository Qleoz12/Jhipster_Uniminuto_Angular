package co.sistemas.transaccionales.uniminuto.repository;
import co.sistemas.transaccionales.uniminuto.domain.ProgramasVuelos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProgramasVuelos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramasVuelosRepository extends JpaRepository<ProgramasVuelos, Long> {

}
