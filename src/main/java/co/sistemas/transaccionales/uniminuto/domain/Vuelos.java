package co.sistemas.transaccionales.uniminuto.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Vuelos.
 */
@Entity
@Table(name = "vuelos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vuelos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "dias_semana")
    private String diasSemana;

    @Column(name = "fecha_salida")
    private ZonedDateTime fechaSalida;

    @Column(name = "fecha_arribo_9")
    private ZonedDateTime fechaArribo9;

    @Column(name = "plazas_vacias")
    private Integer plazasVacias;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "estado_vuelo")
    private Integer estadoVuelo;

    @Column(name = "es_escala")
    private Boolean esEscala;

    @ManyToOne
    @JsonIgnoreProperties("vuelos")
    private Aeropuertos aeropuertoSalida;

    @ManyToOne
    @JsonIgnoreProperties("vuelos")
    private Aeropuertos aeropuertoArribo;

    @ManyToOne
    @JsonIgnoreProperties("vuelos")
    private Aviones idAvion;

    @ManyToOne
    @JsonIgnoreProperties("vuelos")
    private ProgramasVuelos idPrograma;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Vuelos nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDiasSemana() {
        return diasSemana;
    }

    public Vuelos diasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
        return this;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public ZonedDateTime getFechaSalida() {
        return fechaSalida;
    }

    public Vuelos fechaSalida(ZonedDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
        return this;
    }

    public void setFechaSalida(ZonedDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public ZonedDateTime getFechaArribo9() {
        return fechaArribo9;
    }

    public Vuelos fechaArribo9(ZonedDateTime fechaArribo9) {
        this.fechaArribo9 = fechaArribo9;
        return this;
    }

    public void setFechaArribo9(ZonedDateTime fechaArribo9) {
        this.fechaArribo9 = fechaArribo9;
    }

    public Integer getPlazasVacias() {
        return plazasVacias;
    }

    public Vuelos plazasVacias(Integer plazasVacias) {
        this.plazasVacias = plazasVacias;
        return this;
    }

    public void setPlazasVacias(Integer plazasVacias) {
        this.plazasVacias = plazasVacias;
    }

    public Integer getOrden() {
        return orden;
    }

    public Vuelos orden(Integer orden) {
        this.orden = orden;
        return this;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getEstadoVuelo() {
        return estadoVuelo;
    }

    public Vuelos estadoVuelo(Integer estadoVuelo) {
        this.estadoVuelo = estadoVuelo;
        return this;
    }

    public void setEstadoVuelo(Integer estadoVuelo) {
        this.estadoVuelo = estadoVuelo;
    }

    public Boolean isEsEscala() {
        return esEscala;
    }

    public Vuelos esEscala(Boolean esEscala) {
        this.esEscala = esEscala;
        return this;
    }

    public void setEsEscala(Boolean esEscala) {
        this.esEscala = esEscala;
    }

    public Aeropuertos getAeropuertoSalida() {
        return aeropuertoSalida;
    }

    public Vuelos aeropuertoSalida(Aeropuertos aeropuertos) {
        this.aeropuertoSalida = aeropuertos;
        return this;
    }

    public void setAeropuertoSalida(Aeropuertos aeropuertos) {
        this.aeropuertoSalida = aeropuertos;
    }

    public Aeropuertos getAeropuertoArribo() {
        return aeropuertoArribo;
    }

    public Vuelos aeropuertoArribo(Aeropuertos aeropuertos) {
        this.aeropuertoArribo = aeropuertos;
        return this;
    }

    public void setAeropuertoArribo(Aeropuertos aeropuertos) {
        this.aeropuertoArribo = aeropuertos;
    }

    public Aviones getIdAvion() {
        return idAvion;
    }

    public Vuelos idAvion(Aviones aviones) {
        this.idAvion = aviones;
        return this;
    }

    public void setIdAvion(Aviones aviones) {
        this.idAvion = aviones;
    }

    public ProgramasVuelos getIdPrograma() {
        return idPrograma;
    }

    public Vuelos idPrograma(ProgramasVuelos programasVuelos) {
        this.idPrograma = programasVuelos;
        return this;
    }

    public void setIdPrograma(ProgramasVuelos programasVuelos) {
        this.idPrograma = programasVuelos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vuelos)) {
            return false;
        }
        return id != null && id.equals(((Vuelos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vuelos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", diasSemana='" + getDiasSemana() + "'" +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", fechaArribo9='" + getFechaArribo9() + "'" +
            ", plazasVacias=" + getPlazasVacias() +
            ", orden=" + getOrden() +
            ", estadoVuelo=" + getEstadoVuelo() +
            ", esEscala='" + isEsEscala() + "'" +
            "}";
    }
}
