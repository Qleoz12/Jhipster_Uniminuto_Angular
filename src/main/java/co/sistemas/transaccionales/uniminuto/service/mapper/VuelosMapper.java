package co.sistemas.transaccionales.uniminuto.service.mapper;

import co.sistemas.transaccionales.uniminuto.domain.*;
import co.sistemas.transaccionales.uniminuto.service.dto.VuelosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vuelos} and its DTO {@link VuelosDTO}.
 */
@Mapper(componentModel = "spring", uses = {AeropuertosMapper.class, AvionesMapper.class, ProgramasVuelosMapper.class})
public interface VuelosMapper extends EntityMapper<VuelosDTO, Vuelos> {

    @Mapping(source = "aeropuertoSalida.id", target = "aeropuertoSalidaId")
    @Mapping(source = "aeropuertoSalida.nombre", target = "aeropuertoSalidaNombre")
    @Mapping(source = "aeropuertoArribo.id", target = "aeropuertoArriboId")
    @Mapping(source = "aeropuertoArribo.nombre", target = "aeropuertoArriboNombre")
    @Mapping(source = "idAvion.id", target = "idAvionId")
    @Mapping(source = "idAvion.nombre", target = "idAvionNombre")
    @Mapping(source = "idPrograma.id", target = "idProgramaId")
    @Mapping(source = "idPrograma.nombre", target = "idProgramaNombre")
    VuelosDTO toDto(Vuelos vuelos);

    @Mapping(source = "aeropuertoSalidaId", target = "aeropuertoSalida")
    @Mapping(source = "aeropuertoArriboId", target = "aeropuertoArribo")
    @Mapping(source = "idAvionId", target = "idAvion")
    @Mapping(source = "idProgramaId", target = "idPrograma")
    Vuelos toEntity(VuelosDTO vuelosDTO);

    default Vuelos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vuelos vuelos = new Vuelos();
        vuelos.setId(id);
        return vuelos;
    }
}
