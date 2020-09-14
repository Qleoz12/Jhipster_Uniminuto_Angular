package co.sistemas.transaccionales.uniminuto.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.sistemas.transaccionales.uniminuto.domain.ProgramasVuelos} entity.
 */
public class ProgramasVuelosDTO implements Serializable {

    private Long id;

    private String nombre;

    private Integer totalDias;


    private Long aeropuertoSalidaId;

    private String aeropuertoSalidaNombre;

    private Long aeropuertoArriboId;

    private String aeropuertoArriboNombre;

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

    public Integer getTotalDias() {
        return totalDias;
    }

    public void setTotalDias(Integer totalDias) {
        this.totalDias = totalDias;
    }

    public Long getAeropuertoSalidaId() {
        return aeropuertoSalidaId;
    }

    public void setAeropuertoSalidaId(Long aeropuertosId) {
        this.aeropuertoSalidaId = aeropuertosId;
    }

    public String getAeropuertoSalidaNombre() {
        return aeropuertoSalidaNombre;
    }

    public void setAeropuertoSalidaNombre(String aeropuertosNombre) {
        this.aeropuertoSalidaNombre = aeropuertosNombre;
    }

    public Long getAeropuertoArriboId() {
        return aeropuertoArriboId;
    }

    public void setAeropuertoArriboId(Long aeropuertosId) {
        this.aeropuertoArriboId = aeropuertosId;
    }

    public String getAeropuertoArriboNombre() {
        return aeropuertoArriboNombre;
    }

    public void setAeropuertoArriboNombre(String aeropuertosNombre) {
        this.aeropuertoArriboNombre = aeropuertosNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgramasVuelosDTO programasVuelosDTO = (ProgramasVuelosDTO) o;
        if (programasVuelosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), programasVuelosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProgramasVuelosDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", totalDias=" + getTotalDias() +
            ", aeropuertoSalida=" + getAeropuertoSalidaId() +
            ", aeropuertoSalida='" + getAeropuertoSalidaNombre() + "'" +
            ", aeropuertoArribo=" + getAeropuertoArriboId() +
            ", aeropuertoArribo='" + getAeropuertoArriboNombre() + "'" +
            "}";
    }
}
