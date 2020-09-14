package co.sistemas.transaccionales.uniminuto.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.sistemas.transaccionales.uniminuto.domain.Ciudades} entity.
 */
public class CiudadesDTO implements Serializable {

    private Long id;

    private String ciudad;

    private String pais;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CiudadesDTO ciudadesDTO = (CiudadesDTO) o;
        if (ciudadesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ciudadesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CiudadesDTO{" +
            "id=" + getId() +
            ", ciudad='" + getCiudad() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
