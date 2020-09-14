package co.sistemas.transaccionales.uniminuto.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Ciudades.
 */
@Entity
@Table(name = "ciudades")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ciudades implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "pais")
    private String pais;

    @OneToMany(mappedBy = "ciudades")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Aeropuertos> aeropuertos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Ciudades ciudad(String ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public Ciudades pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Set<Aeropuertos> getAeropuertos() {
        return aeropuertos;
    }

    public Ciudades aeropuertos(Set<Aeropuertos> aeropuertos) {
        this.aeropuertos = aeropuertos;
        return this;
    }

    public Ciudades addAeropuertos(Aeropuertos aeropuertos) {
        this.aeropuertos.add(aeropuertos);
        aeropuertos.setCiudades(this);
        return this;
    }

    public Ciudades removeAeropuertos(Aeropuertos aeropuertos) {
        this.aeropuertos.remove(aeropuertos);
        aeropuertos.setCiudades(null);
        return this;
    }

    public void setAeropuertos(Set<Aeropuertos> aeropuertos) {
        this.aeropuertos = aeropuertos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ciudades)) {
            return false;
        }
        return id != null && id.equals(((Ciudades) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ciudades{" +
            "id=" + getId() +
            ", ciudad='" + getCiudad() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
