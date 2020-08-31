package co.sistemas.transaccionales.uniminuto.repository;

import co.sistemas.transaccionales.uniminuto.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
