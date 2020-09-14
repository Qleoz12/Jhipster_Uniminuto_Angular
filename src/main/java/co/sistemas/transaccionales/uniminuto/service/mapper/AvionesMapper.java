package co.sistemas.transaccionales.uniminuto.service.mapper;

import co.sistemas.transaccionales.uniminuto.domain.*;
import co.sistemas.transaccionales.uniminuto.service.dto.AvionesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aviones} and its DTO {@link AvionesDTO}.
 */
@Mapper(componentModel = "spring", uses = {AvionModelosMapper.class})
public interface AvionesMapper extends EntityMapper<AvionesDTO, Aviones> {

    @Mapping(source = "avionModelos.id", target = "avionModelosId")
    @Mapping(source = "avionModelos.nombre", target = "avionModelosNombre")
    AvionesDTO toDto(Aviones aviones);

    @Mapping(source = "avionModelosId", target = "avionModelos")
    Aviones toEntity(AvionesDTO avionesDTO);

    default Aviones fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aviones aviones = new Aviones();
        aviones.setId(id);
        return aviones;
    }
}
