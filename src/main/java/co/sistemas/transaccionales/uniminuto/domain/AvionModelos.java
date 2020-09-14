package co.sistemas.transaccionales.uniminuto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AvionModelos.
 */
@Entity
@Table(name = "avion_modelos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AvionModelos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "capacidad")
    private Integer capacidad;

    @OneToMany(mappedBy = "avionModelos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aviones> aviones = new HashSet<>();

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

    public AvionModelos nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public AvionModelos capacidad(Integer capacidad) {
        this.capacidad = capacidad;
        return this;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Set<Aviones> getAviones() {
        return aviones;
    }

    public AvionModelos aviones(Set<Aviones> aviones) {
        this.aviones = aviones;
        return this;
    }

    public AvionModelos addAviones(Aviones aviones) {
        this.aviones.add(aviones);
        aviones.setAvionModelos(this);
        return this;
    }

    public AvionModelos removeAviones(Aviones aviones) {
        this.aviones.remove(aviones);
        aviones.setAvionModelos(null);
        return this;
    }

    public void setAviones(Set<Aviones> aviones) {
        this.aviones = aviones;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvionModelos)) {
            return false;
        }
        return id != null && id.equals(((AvionModelos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AvionModelos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", capacidad=" + getCapacidad() +
            "}";
    }
}
