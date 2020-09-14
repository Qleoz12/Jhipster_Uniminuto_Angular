package co.sistemas.transaccionales.uniminuto.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.sistemas.transaccionales.uniminuto.domain.Aeropuertos} entity.
 */
public class AeropuertosDTO implements Serializable {

    private Long id;

    private String nombre;


    private Long ciudadesId;

    private String ciudadesCiudad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCiudadesId() {
        return ciudadesId;
    }

    public void setCiudadesId(Long ciudadesId) {
        this.ciudadesId = ciudadesId;
    }

    public String getCiudadesCiudad() {
        return ciudadesCiudad;
    }

    public void setCiudadesCiudad(String ciudadesCiudad) {
        this.ciudadesCiudad = ciudadesCiudad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AeropuertosDTO aeropuertosDTO = (AeropuertosDTO) o;
        if (aeropuertosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aeropuertosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AeropuertosDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", ciudades=" + getCiudadesId() +
            ", ciudades='" + getCiudadesCiudad() + "'" +
            "}";
    }
}
