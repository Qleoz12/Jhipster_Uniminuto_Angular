package co.sistemas.transaccionales.uniminuto.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.sistemas.transaccionales.uniminuto.domain.AvionModelos} entity.
 */
public class AvionModelosDTO implements Serializable {

    private Long id;

    private String nombre;

    private Integer capacidad;


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

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AvionModelosDTO avionModelosDTO = (AvionModelosDTO) o;
        if (avionModelosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), avionModelosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AvionModelosDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", capacidad=" + getCapacidad() +
            "}";
    }
}
