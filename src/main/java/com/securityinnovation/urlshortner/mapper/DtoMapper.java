package com.securityinnovation.urlshortner.mapper;

import com.securityinnovation.urlshortner.entity.audit.UserDateAudit;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Component;

/**
 * <h1>DtoMapper</h1>
 * <p>
 * This Class use for all type of DTO and entity related functionality like DTO to Entity Mapping, Entity to DTO
 * Mapping
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
@Component
public class DtoMapper {

  private final ModelMapper modelMapper;

  public DtoMapper(final ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
    this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
    this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  public <T extends UserDateAudit> T convertToEntity(DTOEntity dto, Class<T> entityClass) {
    return convertToEntityWithStrategy(dto, entityClass, MatchingStrategies.STRICT);
  }

  public <T extends UserDateAudit> T convertToEntityWithStrategy(DTOEntity dto, Class<T> entityClass,
    MatchingStrategy matchingStrategy) {
    this.modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
    return modelMapper.map(dto, entityClass);
  }

  public <D, E> List<E> convertDTOListToEntityList(final List<D> dtoList, final Class<E> entityClass) {
    return convertDTOListToEntityListWithStrategy(dtoList, entityClass, MatchingStrategies.STRICT);
  }

  public <D, E> List<E> convertDTOListToEntityListWithStrategy(final List<D> dtoList, final Class<E> entityClass,
    MatchingStrategy matchingStrategy) {
    this.modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
    return dtoList.stream().map(dto -> modelMapper.map(dto, entityClass)).collect(Collectors.toList());
  }

  public <E, D> List<D> convertEntityListToDTOList(final List<E> entityList, final Class<D> dtoClass) {
    return convertEntityListToDTOListWithStrategy(entityList, dtoClass, MatchingStrategies.STRICT);
  }

  public <E, D> List<D> convertEntityListToDTOListWithStrategy(final List<E> entityList, final Class<D> dtoClass,
    MatchingStrategy matchingStrategy) {
    this.modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
    return entityList.stream().map(entity -> modelMapper.map(entity, dtoClass)).collect(Collectors.toList());
  }

  public <E extends UserDateAudit, D> D convertToDto(E entity, Class<D> dtoClass) {
    return convertToDtoWithStrategy(entity, dtoClass, MatchingStrategies.STRICT);
  }

  public <E extends UserDateAudit, D> D convertToDtoWithStrategy(E entity, Class<D> dtoClass,
    MatchingStrategy matchingStrategy) {
    this.modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
    return modelMapper.map(entity, dtoClass);
  }

  public <T extends UserDateAudit> void updateToEntity(DTOEntity dto, T entity) {
    updateToEntityWithStrategy(dto, entity, MatchingStrategies.STRICT);
  }

  public <T extends UserDateAudit> void updateToEntityWithStrategy(DTOEntity dto, T entity,
    MatchingStrategy matchingStrategy) {
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
    modelMapper.map(dto, entity);
  }
}
