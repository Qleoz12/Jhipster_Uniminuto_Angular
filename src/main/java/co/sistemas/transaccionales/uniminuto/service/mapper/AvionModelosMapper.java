package co.sistemas.transaccionales.uniminuto.service.mapper;

import co.sistemas.transaccionales.uniminuto.domain.*;
import co.sistemas.transaccionales.uniminuto.service.dto.AvionModelosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AvionModelos} and its DTO {@link AvionModelosDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AvionModelosMapper extends EntityMapper<AvionModelosDTO, AvionModelos> {


    @Mapping(target = "aviones", ignore = true)
    @Mapping(target = "removeAviones", ignore = true)
    AvionModelos toEntity(AvionModelosDTO avionModelosDTO);

    default AvionModelos fromId(Long id) {
        if (id == null) {
            return null;
        }
        AvionModelos avionModelos = new AvionModelos();
        avionModelos.setId(id);
        return avionModelos;
    }
}
