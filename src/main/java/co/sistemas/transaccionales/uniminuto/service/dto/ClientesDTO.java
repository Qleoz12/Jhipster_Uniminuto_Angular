package co.sistemas.transaccionales.uniminuto.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.sistemas.transaccionales.uniminuto.domain.Clientes} entity.
 */
public class ClientesDTO implements Serializable {

    private Long id;

    private String nombres;

    private String appelidos;

    private String documentoTipo;

    private String documentoNumero;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAppelidos() {
        return appelidos;
    }

    public void setAppelidos(String appelidos) {
        this.appelidos = appelidos;
    }

    public String getDocumentoTipo() {
        return documentoTipo;
    }

    public void setDocumentoTipo(String documentoTipo) {
        this.documentoTipo = documentoTipo;
    }

    public String getDocumentoNumero() {
        return documentoNumero;
    }

    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientesDTO clientesDTO = (ClientesDTO) o;
        if (clientesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientesDTO{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", appelidos='" + getAppelidos() + "'" +
            ", documentoTipo='" + getDocumentoTipo() + "'" +
            ", documentoNumero='" + getDocumentoNumero() + "'" +
            "}";
    }
}
