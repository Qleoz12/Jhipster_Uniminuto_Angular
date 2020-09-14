package co.sistemas.transaccionales.uniminuto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Clientes.
 */
@Entity
@Table(name = "clientes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "appelidos")
    private String appelidos;

    @Column(name = "documento_tipo")
    private String documentoTipo;

    @Column(name = "documento_numero")
    private String documentoNumero;

    @OneToMany(mappedBy = "clientes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tickets> tickets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public Clientes nombres(String nombres) {
        this.nombres = nombres;
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAppelidos() {
        return appelidos;
    }

    public Clientes appelidos(String appelidos) {
        this.appelidos = appelidos;
        return this;
    }

    public void setAppelidos(String appelidos) {
        this.appelidos = appelidos;
    }

    public String getDocumentoTipo() {
        return documentoTipo;
    }

    public Clientes documentoTipo(String documentoTipo) {
        this.documentoTipo = documentoTipo;
        return this;
    }

    public void setDocumentoTipo(String documentoTipo) {
        this.documentoTipo = documentoTipo;
    }

    public String getDocumentoNumero() {
        return documentoNumero;
    }

    public Clientes documentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
        return this;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    public Set<Tickets> getTickets() {
        return tickets;
    }

    public Clientes tickets(Set<Tickets> tickets) {
        this.tickets = tickets;
        return this;
    }

    public Clientes addTickets(Tickets tickets) {
        this.tickets.add(tickets);
        tickets.setClientes(this);
        return this;
    }

    public Clientes removeTickets(Tickets tickets) {
        this.tickets.remove(tickets);
        tickets.setClientes(null);
        return this;
    }

    public void setTickets(Set<Tickets> tickets) {
        this.tickets = tickets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clientes)) {
            return false;
        }
        return id != null && id.equals(((Clientes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Clientes{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", appelidos='" + getAppelidos() + "'" +
            ", documentoTipo='" + getDocumentoTipo() + "'" +
            ", documentoNumero='" + getDocumentoNumero() + "'" +
            "}";
    }
}
