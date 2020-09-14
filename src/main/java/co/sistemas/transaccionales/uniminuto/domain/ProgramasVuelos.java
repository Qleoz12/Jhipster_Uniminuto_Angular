package co.sistemas.transaccionales.uniminuto.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProgramasVuelos.
 */
@Entity
@Table(name = "programas_vuelos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProgramasVuelos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "total_dias")
    private Integer totalDias;

    @OneToMany(mappedBy = "programasVuelos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tickets> tickets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("programasVuelos")
    private Aeropuertos aeropuertoSalida;

    @ManyToOne
    @JsonIgnoreProperties("programasVuelos")
    private Aeropuertos aeropuertoArribo;

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

    public ProgramasVuelos nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTotalDias() {
        return totalDias;
    }

    public ProgramasVuelos totalDias(Integer totalDias) {
        this.totalDias = totalDias;
        return this;
    }

    public void setTotalDias(Integer totalDias) {
        this.totalDias = totalDias;
    }

    public Set<Tickets> getTickets() {
        return tickets;
    }

    public ProgramasVuelos tickets(Set<Tickets> tickets) {
        this.tickets = tickets;
        return this;
    }

    public ProgramasVuelos addTickets(Tickets tickets) {
        this.tickets.add(tickets);
        tickets.setProgramasVuelos(this);
        return this;
    }

    public ProgramasVuelos removeTickets(Tickets tickets) {
        this.tickets.remove(tickets);
        tickets.setProgramasVuelos(null);
        return this;
    }

    public void setTickets(Set<Tickets> tickets) {
        this.tickets = tickets;
    }

    public Aeropuertos getAeropuertoSalida() {
        return aeropuertoSalida;
    }

    public ProgramasVuelos aeropuertoSalida(Aeropuertos aeropuertos) {
        this.aeropuertoSalida = aeropuertos;
        return this;
    }

    public void setAeropuertoSalida(Aeropuertos aeropuertos) {
        this.aeropuertoSalida = aeropuertos;
    }

    public Aeropuertos getAeropuertoArribo() {
        return aeropuertoArribo;
    }

    public ProgramasVuelos aeropuertoArribo(Aeropuertos aeropuertos) {
        this.aeropuertoArribo = aeropuertos;
        return this;
    }

    public void setAeropuertoArribo(Aeropuertos aeropuertos) {
        this.aeropuertoArribo = aeropuertos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramasVuelos)) {
            return false;
        }
        return id != null && id.equals(((ProgramasVuelos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProgramasVuelos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", totalDias=" + getTotalDias() +
            "}";
    }
}
