package co.sistemas.transaccionales.uniminuto.service.mapper;

import co.sistemas.transaccionales.uniminuto.domain.*;
import co.sistemas.transaccionales.uniminuto.service.dto.CiudadesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ciudades} and its DTO {@link CiudadesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CiudadesMapper extends EntityMapper<CiudadesDTO, Ciudades> {


    @Mapping(target = "aeropuertos", ignore = true)
    @Mapping(target = "removeAeropuertos", ignore = true)
    Ciudades toEntity(CiudadesDTO ciudadesDTO);

    default Ciudades fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ciudades ciudades = new Ciudades();
        ciudades.setId(id);
        return ciudades;
    }
}
