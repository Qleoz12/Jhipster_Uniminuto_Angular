package co.sistemas.transaccionales.uniminuto.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Tickets.
 */
@Entity
@Table(name = "tickets")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tickets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "valor")
    private String valor;

    @Column(name = "descuento")
    private Integer descuento;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @ManyToOne
    @JsonIgnoreProperties("tickets")
    private Clientes clientes;

    @ManyToOne
    @JsonIgnoreProperties("tickets")
    private ProgramasVuelos programasVuelos;

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

    public Tickets nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public Tickets valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public Tickets descuento(Integer descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Tickets estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Tickets fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public Tickets clientes(Clientes clientes) {
        this.clientes = clientes;
        return this;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public ProgramasVuelos getProgramasVuelos() {
        return programasVuelos;
    }

    public Tickets programasVuelos(ProgramasVuelos programasVuelos) {
        this.programasVuelos = programasVuelos;
        return this;
    }

    public void setProgramasVuelos(ProgramasVuelos programasVuelos) {
        this.programasVuelos = programasVuelos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tickets)) {
            return false;
        }
        return id != null && id.equals(((Tickets) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tickets{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", valor='" + getValor() + "'" +
            ", descuento=" + getDescuento() +
            ", estado='" + isEstado() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
