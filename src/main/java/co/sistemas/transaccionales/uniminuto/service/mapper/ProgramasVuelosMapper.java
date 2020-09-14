package co.sistemas.transaccionales.uniminuto.service.mapper;

import co.sistemas.transaccionales.uniminuto.domain.*;
import co.sistemas.transaccionales.uniminuto.service.dto.ProgramasVuelosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProgramasVuelos} and its DTO {@link ProgramasVuelosDTO}.
 */
@Mapper(componentModel = "spring", uses = {AeropuertosMapper.class})
public interface ProgramasVuelosMapper extends EntityMapper<ProgramasVuelosDTO, ProgramasVuelos> {

    @Mapping(source = "aeropuertoSalida.id", target = "aeropuertoSalidaId")
    @Mapping(source = "aeropuertoSalida.nombre", target = "aeropuertoSalidaNombre")
    @Mapping(source = "aeropuertoArribo.id", target = "aeropuertoArriboId")
    @Mapping(source = "aeropuertoArribo.nombre", target = "aeropuertoArriboNombre")
    ProgramasVuelosDTO toDto(ProgramasVuelos programasVuelos);

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "removeTickets", ignore = true)
    @Mapping(source = "aeropuertoSalidaId", target = "aeropuertoSalida")
    @Mapping(source = "aeropuertoArriboId", target = "aeropuertoArribo")
    ProgramasVuelos toEntity(ProgramasVuelosDTO programasVuelosDTO);

    default ProgramasVuelos fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProgramasVuelos programasVuelos = new ProgramasVuelos();
        programasVuelos.setId(id);
        return programasVuelos;
    }
}
