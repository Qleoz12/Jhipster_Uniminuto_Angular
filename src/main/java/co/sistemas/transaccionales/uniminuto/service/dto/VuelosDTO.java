package co.sistemas.transaccionales.uniminuto.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.sistemas.transaccionales.uniminuto.domain.Vuelos} entity.
 */
public class VuelosDTO implements Serializable {

    private Long id;

    private String nombre;

    private String diasSemana;

    private ZonedDateTime fechaSalida;

    private ZonedDateTime fechaArribo9;

    private Integer plazasVacias;

    private Integer orden;

    private Integer estadoVuelo;

    private Boolean esEscala;


    private Long aeropuertoSalidaId;

    private String aeropuertoSalidaNombre;

    private Long aeropuertoArriboId;

    private String aeropuertoArriboNombre;

    private Long idAvionId;

    private String idAvionNombre;

    private Long idProgramaId;

    private String idProgramaNombre;

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

    public String getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public ZonedDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(ZonedDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public ZonedDateTime getFechaArribo9() {
        return fechaArribo9;
    }

    public void setFechaArribo9(ZonedDateTime fechaArribo9) {
        this.fechaArribo9 = fechaArribo9;
    }

    public Integer getPlazasVacias() {
        return plazasVacias;
    }

    public void setPlazasVacias(Integer plazasVacias) {
        this.plazasVacias = plazasVacias;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getEstadoVuelo() {
        return estadoVuelo;
    }

    public void setEstadoVuelo(Integer estadoVuelo) {
        this.estadoVuelo = estadoVuelo;
    }

    public Boolean isEsEscala() {
        return esEscala;
    }

    public void setEsEscala(Boolean esEscala) {
        this.esEscala = esEscala;
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

    public Long getIdAvionId() {
        return idAvionId;
    }

    public void setIdAvionId(Long avionesId) {
        this.idAvionId = avionesId;
    }

    public String getIdAvionNombre() {
        return idAvionNombre;
    }

    public void setIdAvionNombre(String avionesNombre) {
        this.idAvionNombre = avionesNombre;
    }

    public Long getIdProgramaId() {
        return idProgramaId;
    }

    public void setIdProgramaId(Long programasVuelosId) {
        this.idProgramaId = programasVuelosId;
    }

    public String getIdProgramaNombre() {
        return idProgramaNombre;
    }

    public void setIdProgramaNombre(String programasVuelosNombre) {
        this.idProgramaNombre = programasVuelosNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VuelosDTO vuelosDTO = (VuelosDTO) o;
        if (vuelosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vuelosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VuelosDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", diasSemana='" + getDiasSemana() + "'" +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", fechaArribo9='" + getFechaArribo9() + "'" +
            ", plazasVacias=" + getPlazasVacias() +
            ", orden=" + getOrden() +
            ", estadoVuelo=" + getEstadoVuelo() +
            ", esEscala='" + isEsEscala() + "'" +
            ", aeropuertoSalida=" + getAeropuertoSalidaId() +
            ", aeropuertoSalida='" + getAeropuertoSalidaNombre() + "'" +
            ", aeropuertoArribo=" + getAeropuertoArriboId() +
            ", aeropuertoArribo='" + getAeropuertoArriboNombre() + "'" +
            ", idAvion=" + getIdAvionId() +
            ", idAvion='" + getIdAvionNombre() + "'" +
            ", idPrograma=" + getIdProgramaId() +
            ", idPrograma='" + getIdProgramaNombre() + "'" +
            "}";
    }
}
