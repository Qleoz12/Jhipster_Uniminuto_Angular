package co.sistemas.transaccionales.uniminuto.service.mapper;

import co.sistemas.transaccionales.uniminuto.domain.*;
import co.sistemas.transaccionales.uniminuto.service.dto.AeropuertosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aeropuertos} and its DTO {@link AeropuertosDTO}.
 */
@Mapper(componentModel = "spring", uses = {CiudadesMapper.class})
public interface AeropuertosMapper extends EntityMapper<AeropuertosDTO, Aeropuertos> {

    @Mapping(source = "ciudades.id", target = "ciudadesId")
    @Mapping(source = "ciudades.ciudad", target = "ciudadesCiudad")
    AeropuertosDTO toDto(Aeropuertos aeropuertos);

    @Mapping(source = "ciudadesId", target = "ciudades")
    Aeropuertos toEntity(AeropuertosDTO aeropuertosDTO);

    default Aeropuertos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aeropuertos aeropuertos = new Aeropuertos();
        aeropuertos.setId(id);
        return aeropuertos;
    }
}
