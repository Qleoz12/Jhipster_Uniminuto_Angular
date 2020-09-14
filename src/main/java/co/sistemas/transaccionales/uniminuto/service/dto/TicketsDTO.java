package co.sistemas.transaccionales.uniminuto.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.sistemas.transaccionales.uniminuto.domain.Tickets} entity.
 */
public class TicketsDTO implements Serializable {

    private Long id;

    private String nombre;

    private String valor;

    private Integer descuento;

    private Boolean estado;

    private ZonedDateTime fecha;


    private Long clientesId;

    private String clientesNombres;

    private Long programasVuelosId;

    private String programasVuelosNombre;

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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Long getClientesId() {
        return clientesId;
    }

    public void setClientesId(Long clientesId) {
        this.clientesId = clientesId;
    }

    public String getClientesNombres() {
        return clientesNombres;
    }

    public void setClientesNombres(String clientesNombres) {
        this.clientesNombres = clientesNombres;
    }

    public Long getProgramasVuelosId() {
        return programasVuelosId;
    }

    public void setProgramasVuelosId(Long programasVuelosId) {
        this.programasVuelosId = programasVuelosId;
    }

    public String getProgramasVuelosNombre() {
        return programasVuelosNombre;
    }

    public void setProgramasVuelosNombre(String programasVuelosNombre) {
        this.programasVuelosNombre = programasVuelosNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketsDTO ticketsDTO = (TicketsDTO) o;
        if (ticketsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticketsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TicketsDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", valor='" + getValor() + "'" +
            ", descuento=" + getDescuento() +
            ", estado='" + isEstado() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", clientes=" + getClientesId() +
            ", clientes='" + getClientesNombres() + "'" +
            ", programasVuelos=" + getProgramasVuelosId() +
            ", programasVuelos='" + getProgramasVuelosNombre() + "'" +
            "}";
    }
}
