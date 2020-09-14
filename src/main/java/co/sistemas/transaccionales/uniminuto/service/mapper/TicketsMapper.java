package co.sistemas.transaccionales.uniminuto.service.mapper;

import co.sistemas.transaccionales.uniminuto.domain.*;
import co.sistemas.transaccionales.uniminuto.service.dto.TicketsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tickets} and its DTO {@link TicketsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientesMapper.class, ProgramasVuelosMapper.class})
public interface TicketsMapper extends EntityMapper<TicketsDTO, Tickets> {

    @Mapping(source = "clientes.id", target = "clientesId")
    @Mapping(source = "clientes.nombres", target = "clientesNombres")
    @Mapping(source = "programasVuelos.id", target = "programasVuelosId")
    @Mapping(source = "programasVuelos.nombre", target = "programasVuelosNombre")
    TicketsDTO toDto(Tickets tickets);

    @Mapping(source = "clientesId", target = "clientes")
    @Mapping(source = "programasVuelosId", target = "programasVuelos")
    Tickets toEntity(TicketsDTO ticketsDTO);

    default Tickets fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tickets tickets = new Tickets();
        tickets.setId(id);
        return tickets;
    }
}
