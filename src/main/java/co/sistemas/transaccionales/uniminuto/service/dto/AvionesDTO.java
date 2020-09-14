package co.sistemas.transaccionales.uniminuto.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.sistemas.transaccionales.uniminuto.domain.Aviones} entity.
 */
public class AvionesDTO implements Serializable {

    private Long id;

    private String nombre;


    private Long avionModelosId;

    private String avionModelosNombre;

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

    public Long getAvionModelosId() {
        return avionModelosId;
    }

    public void setAvionModelosId(Long avionModelosId) {
        this.avionModelosId = avionModelosId;
    }

    public String getAvionModelosNombre() {
        return avionModelosNombre;
    }

    public void setAvionModelosNombre(String avionModelosNombre) {
        this.avionModelosNombre = avionModelosNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AvionesDTO avionesDTO = (AvionesDTO) o;
        if (avionesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), avionesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AvionesDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", avionModelos=" + getAvionModelosId() +
            ", avionModelos='" + getAvionModelosNombre() + "'" +
            "}";
    }
}
