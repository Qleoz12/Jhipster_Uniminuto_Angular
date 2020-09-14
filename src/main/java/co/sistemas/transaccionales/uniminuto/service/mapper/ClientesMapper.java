package co.sistemas.transaccionales.uniminuto.service.mapper;

import co.sistemas.transaccionales.uniminuto.domain.*;
import co.sistemas.transaccionales.uniminuto.service.dto.ClientesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clientes} and its DTO {@link ClientesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientesMapper extends EntityMapper<ClientesDTO, Clientes> {


    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "removeTickets", ignore = true)
    Clientes toEntity(ClientesDTO clientesDTO);

    default Clientes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Clientes clientes = new Clientes();
        clientes.setId(id);
        return clientes;
    }
}
